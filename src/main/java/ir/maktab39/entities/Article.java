package ir.maktab39.entities;


import ir.maktab39.base.entity.BaseEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Article extends BaseEntity<Long> {

    @Column(unique = true)
    private String title;
    private String brief;
    private String content;
    private Date createDate;
    private Date lastUpdateDate;
    private Date publishDate;
    private boolean isPublished;
    @ManyToOne
    private User author;
    //    @ManyToOne
    @Transient
    private Category category;
    private Long categoryId;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Tag> tags;

    @Override
    public String toString() {
        return "id=" + id +
                "\n" + "title=" + title +
                "\n" + "brief=" + brief +
                "\n" + "content=" + content +
                "\n" + "createDate=" + createDate +
                "\n" + "isPublished=" + isPublished +
                "\n" + "author=" + (author != null ? author.getUsername() : "") +
                "\n" + "category=" + (category != null ? category.getTitle() : "");
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
