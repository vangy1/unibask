package sk.unibask.data.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Category {
    @Id
    private Long id;
    private String title;
    @Column(columnDefinition = "varchar(1000)")
    private String subtitle;
    private String icon;

    @ManyToOne
    private Category parentCategory;

    @OneToMany(mappedBy = "category")
    private List<Question> questions;

    @OneToMany(mappedBy = "parentCategory")
    private List<Category> childrenCategories;

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

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Category> getChildrenCategories() {
        return childrenCategories;
    }

    public void setChildrenCategories(List<Category> childrenCategories) {
        this.childrenCategories = childrenCategories;
    }
}
