package sk.unibask.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import sk.unibask.authentication.AuthenticationService;
import sk.unibask.data.model.Account;
import sk.unibask.data.model.Category;
import sk.unibask.data.repository.AccountRepository;
import sk.unibask.data.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryService categoryService;

    private final CategoryRepository categoryRepository;
    private final AuthenticationService authenticationService;
    private final AccountRepository accountRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, AuthenticationService authenticationService, AccountRepository accountRepository) {
        this.categoryRepository = categoryRepository;
        this.authenticationService = authenticationService;
        this.accountRepository = accountRepository;
    }

    public void changeFollowStatus(String categoryId, boolean followed) {
        if (followed) {
            categoryService.followCategory(categoryId);
        } else {
            categoryService.unfollowCategory(categoryId);
        }
    }

    @Transactional
    public CategoryDto getCategory(Long id) {
        authenticationService.getLoggedAccount();
        return categoryRepository.findById(id).map(category -> new CategoryDto(category.getId(), category.getTitle(), false)).orElse(null);
    }

    @Transactional
    public void followCategory(String categoryId) {
        var account = authenticationService.getLoggedAccount();
        Category category = categoryRepository.findById(Long.valueOf(categoryId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Kategória neexistuje."));
        account.getFollowingCategories().add(category);
        accountRepository.save(account);
    }

    @Transactional
    public void unfollowCategory(String categoryId) {
        var account = authenticationService.getLoggedAccount();
        Category category = categoryRepository.findById(Long.valueOf(categoryId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Kategória neexistuje."));
        account.getFollowingCategories().remove(category);
        accountRepository.save(account);
    }

    @Transactional
    public List<CategoryDto> getCategories() {
        authenticationService.getLoggedAccount();
        List<CategoryDto> rootCategoryDtos = new ArrayList<>();
        var account = authenticationService.getLoggedAccount();
        for (Category rootCategory : categoryRepository.findRoots()) {
            var rootCategoryDto = new CategoryDto(rootCategory.getId(), rootCategory.getTitle(), account.getFollowingCategories().contains(rootCategory));
            rootCategoryDtos.add(rootCategoryDto);
            setChildrenCategories(rootCategory, rootCategoryDto, account);
        }

        for (CategoryDto rootCategoryDto : rootCategoryDtos) {
            recountQuestions(rootCategoryDto);
            setPaths(rootCategoryDto, new ArrayList<>());
        }

        return rootCategoryDtos;
    }


    @Transactional
    public List<CategoryDto> getLeafCategories() {
        authenticationService.getLoggedAccount();
        List<CategoryDto> categories = categoryService.getCategories();
        List<CategoryDto> leafCategories = new ArrayList<>();
        for (CategoryDto category : categories) {
            filterLeafCategories(category, leafCategories);
        }
        return leafCategories;
    }


    @Transactional
    public void setChildrenCategories(Category parentCategory, CategoryDto parentCategoryDto, Account account) {
        List<Category> childrenCategories = parentCategory.getChildrenCategories();
        parentCategoryDto.setQuestionCount((long) parentCategory.getQuestions().size());

        List<CategoryDto> childrenCategoryDtos = new ArrayList<>();
        for (Category childrenCategory : childrenCategories) {
            childrenCategoryDtos.add(new CategoryDto(childrenCategory.getId(), childrenCategory.getTitle(), account.getFollowingCategories().contains(childrenCategory)));
        }

        parentCategoryDto.setChildrenCategories(childrenCategoryDtos);

        for (var i = 0; i < childrenCategories.size(); i++) {
            categoryService.setChildrenCategories(childrenCategories.get(i), childrenCategoryDtos.get(i), account);
        }
    }


    private void filterLeafCategories(CategoryDto category, List<CategoryDto> leafCategories) {
        List<CategoryDto> childrenCategories = category.getChildrenCategories();
        if (childrenCategories.isEmpty()) {
            leafCategories.add(category);
        } else {
            for (CategoryDto childrenCategory : childrenCategories) {
                filterLeafCategories(childrenCategory, leafCategories);
            }
        }
    }

    private void recountQuestions(CategoryDto root) {
        if (root.getChildrenCategories() == null) return;
        root.getChildrenCategories().forEach(this::recountQuestions);
        root.setQuestionCount(
                root.getQuestionCount() +
                        root.getChildrenCategories().stream()
                                .map(CategoryDto::getQuestionCount)
                                .reduce(0L, Long::sum));
    }

    private void setPaths(CategoryDto root, List<String> path) {
        root.getPath().addAll(path);

        ArrayList<String> newPath = new ArrayList<>(path);
        newPath.add(root.getTitle());

        for (CategoryDto childrenCategory : root.getChildrenCategories()) {
            setPaths(childrenCategory, newPath);
        }
    }
}
