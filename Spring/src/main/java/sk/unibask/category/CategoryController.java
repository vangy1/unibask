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
    public CategoryDto getCategory(@RequestParam("id") Long id) {
        return categoryService.getCategory(id);
    }

    @PostMapping("/category/follow")
    public void changeFollowStatus(@RequestBody Map<String, String> body) {
        categoryService.changeFollowStatus(body.get("id"), Boolean.valueOf(body.get("followed")));
    }
}
