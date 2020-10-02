package ir.maktab39.dto;

public class ArticleSearchDto {

    private String title;
    private String brief;
    private String content;
    private boolean isPublished;
    private Long authorId;
//    private Long categoryId;
    private Long oneOfTagsId;

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

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

//    public Long getCategoryId() {
//        return categoryId;
//    }
//
//    public void setCategoryId(Long categoryId) {
//        this.categoryId = categoryId;
//    }

    public Long getOneOfTagsId() {
        return oneOfTagsId;
    }

    public void setOneOfTagsId(Long oneOfTagsId) {
        this.oneOfTagsId = oneOfTagsId;
    }
}
