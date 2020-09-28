package ir.maktab39.entities;

import ir.maktab39.base.entity.BaseEntity;

import javax.persistence.*;

@Entity
public class Category extends BaseEntity<Long> {

    @Column(unique = true)
    private String title;
    private String description;

    @Override
    public String toString() {
        return "id=" + id +
                "\n" + "title =" + title +
                "\n" + "description=" + description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
