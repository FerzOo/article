package ir.maktab39.entities;

import ir.maktab39.base.entity.BaseEntity;

import javax.persistence.*;

@Entity
public class Role extends BaseEntity<Long> {

    @Column(unique = true)
    private String title;

    @Override
    public String toString() {
        return "id=" + id +
                "\n" + "title=" + title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
