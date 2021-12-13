package sk.unibask.category;

import java.util.ArrayList;
import java.util.List;

public class CategoryDto {
    private Long id;
    private String title;
    private List<CategoryDto> childrenCategories;
    private Long questionCount;
    private List<String> path;
    private Boolean isFollowed;

    public CategoryDto(Long id, String title, Boolean isFollowed) {
        this.id = id;
        this.title = title;
        this.isFollowed = isFollowed;
        this.path = new ArrayList<>();
        this.childrenCategories = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<CategoryDto> getChildrenCategories() {
        return childrenCategories;
    }

    public void setChildrenCategories(List<CategoryDto> childrenCategories) {
        this.childrenCategories = childrenCategories;
    }

    public Long getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Long questionCount) {
        this.questionCount = questionCount;
    }


    public List<String> getPath() {
        return path;
    }

    public void setPath(List<String> path) {
        this.path = path;
    }

    public Boolean getFollowed() {
        return isFollowed;
    }

    public void setFollowed(Boolean followed) {
        isFollowed = followed;
    }
}
