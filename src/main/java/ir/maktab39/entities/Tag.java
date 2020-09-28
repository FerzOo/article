package ir.maktab39.entities;

import ir.maktab39.base.entity.BaseEntity;

import javax.persistence.*;

@Entity
public class Tag extends BaseEntity<Long> {

    @Column(unique = true)
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
