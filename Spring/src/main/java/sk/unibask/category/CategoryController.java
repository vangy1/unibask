package sk.unibask.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public List<CategoryDto> getCategories(@RequestParam(value = "leaf", required = false) String leaf) {
        if ("true".equals(leaf)) {
            return categoryService.getLeafCategories();
        } else {
            return categoryService.getCategories();
        }
    }

    @GetMapping("/category")
    public CategoryDto getCategory(@RequestParam("id") String id) {
        return categoryService.getCategory();
    }

    @PostMapping("/category/favorite")
    public void makeCategoryFavorite(@RequestBody Map<String, String> body) {
        categoryService.makeCategoryFavorite(body.get("id"));
    }

    @PostMapping("/category")
    public String createNewCategory(@RequestBody Map<String, String> body) {
        var newCategory = categoryService.createNewCategory(body.get("parentCategoryId"), body.get("title"));
        return String.valueOf(newCategory.getId());
    }
}
