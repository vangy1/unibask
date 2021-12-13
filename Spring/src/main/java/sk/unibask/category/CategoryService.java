package sk.unibask.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public Category createNewCategory(String parentCategoryId, String title) {
        var parentCategory = categoryRepository.findById(Long.valueOf(parentCategoryId)).orElseThrow();

        var newCategory = new Category();
        newCategory.setTitle(title);
        categoryRepository.save(newCategory);

        List<Category> childrenCategoriesOfParent = parentCategory.getChildrenCategories();
        childrenCategoriesOfParent.add(newCategory);
        parentCategory.setChildrenCategories(childrenCategoriesOfParent);
        categoryRepository.save(parentCategory);

        return newCategory;
    }

    public void changeFollowStatus(String categoryId, boolean followed) {
        if (followed) {
            categoryService.followCategory(categoryId);
        } else {
            categoryService.unfollowCategory(categoryId);
        }
    }

    @Transactional
    public void followCategory(String categoryId) {
        var account = authenticationService.getLoggedAccount();
        Category category = categoryRepository.findById(Long.valueOf(categoryId)).get();
        account.getFollowingCategories().add(category);
        accountRepository.save(account);
    }

    @Transactional
    public void unfollowCategory(String categoryId) {
        var account = authenticationService.getLoggedAccount();
        Category category = categoryRepository.findById(Long.valueOf(categoryId)).get();
        account.getFollowingCategories().remove(category);
        accountRepository.save(account);
    }

    @Transactional
    public List<CategoryDto> getCategories() {
        List<CategoryDto> rootCategoryDtos = new ArrayList<>();
        var account = authenticationService.getLoggedAccount();
        for (Category rootCategory : categoryRepository.findRoots()) {
            var rootCategoryDto = new CategoryDto(rootCategory.getId(), rootCategory.getTitle(), account.getFollowingCategories().contains(rootCategory));
            rootCategoryDtos.add(rootCategoryDto);
            setChildrenCategories(rootCategory, rootCategoryDto, account);
        }

        for (CategoryDto rootCategoryDto : rootCategoryDtos) {
            recountQuestions(rootCategoryDto);
//            setFavorites(rootCategoryDto);
            setPaths(rootCategoryDto, new ArrayList<>());
        }

        return rootCategoryDtos;
    }


    public List<CategoryDto> getLeafCategories() {
        List<CategoryDto> categories = categoryService.getCategories();

        List<CategoryDto> leafCategories = new ArrayList<>();
        for (CategoryDto category : categories) {
            filterLeafCategories(category, leafCategories);
        }

        return leafCategories;
    }

    public void filterLeafCategories(CategoryDto category, List<CategoryDto> leafCategories) {
        List<CategoryDto> childrenCategories = category.getChildrenCategories();
        if (childrenCategories.isEmpty()) {
            leafCategories.add(category);
        } else {
            for (CategoryDto childrenCategory : childrenCategories) {
                filterLeafCategories(childrenCategory, leafCategories);
            }
        }
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

    private void recountQuestions(CategoryDto root) {
        if (root.getChildrenCategories() == null) return;
        root.getChildrenCategories().forEach(this::recountQuestions);
        root.setQuestionCount(
                root.getQuestionCount() +
                        root.getChildrenCategories().stream()
                                .map(CategoryDto::getQuestionCount)
                                .reduce(0L, Long::sum));
    }

//    private void setFavorites(CategoryDto root) {
//        if (root.getChildrenCategories() == null) return;
//        root.getChildrenCategories().forEach(this::setFavorites);
//        root.setFavorite(root.getChildrenCategories().stream().allMatch(CategoryDto::isFavorite));
//    }

    private void setPaths(CategoryDto root, List<String> path) {
        root.getPath().addAll(path);

        ArrayList<String> newPath = new ArrayList<>(path);
        newPath.add(root.getTitle());

        for (CategoryDto childrenCategory : root.getChildrenCategories()) {
            setPaths(childrenCategory, newPath);
        }
    }

//    @Transactional
//    public Date getLastActivity(Category category) {
//        Date lastActivity = null;
////        for (Entry entry : categoryRepository.findAllEntries(category)) {
//        for (Entry entry : categoryService.getEntriesOfCategory(category)) {
//            if (lastActivity == null || entry.getCreationDate().after(lastActivity)) {
//                lastActivity = entry.getCreationDate();
//            }
//        }
//        return lastActivity;
//    }
//
//    @Transactional
//    public List<Entry> getEntriesOfCategory(Category category) {
//        List<Entry> entries = new ArrayList<>();
//        List<Question> questions = category.getQuestions();
//        for (Question question : questions) {
//            List<Answer> answers = question.getAnswers();
//            for (Answer answer : answers) {
//                entries.addAll(answer.getComments());
//            }
//            entries.addAll(answers);
//        }
//        entries.addAll(questions);
//        return entries;
//    }

    public CategoryDto getCategory() {
        return null;
    }


}
