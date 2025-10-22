package site.hohyun.api.news.domain;

import java.time.LocalDateTime;

/**
 * 공지사항 엔티티
 * 데이터베이스와 직접 매핑되는 도메인 객체
 */
public class NewsEntity 
{
    private Long id;
    private String title;
    private String content;
    private String author;
    private String category;
    private Boolean isImportant;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer viewCount;

    // 기본 생성자
    public NewsEntity() {}

    // 전체 생성자
    public NewsEntity(Long id, String title, String content, String author, 
                      String category, Boolean isImportant, LocalDateTime createdAt, 
                      LocalDateTime updatedAt, Integer viewCount) 
    {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.category = category;
        this.isImportant = isImportant;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.viewCount = viewCount;
    }

    // Getter
    public Long getId() 
    {
        return id;
    }

    public String getTitle() 
    {
        return title;
    }

    public String getContent() 
    {
        return content;
    }

    public String getAuthor() 
    {
        return author;
    }

    public String getCategory() 
    {
        return category;
    }

    public Boolean getIsImportant() 
    {
        return isImportant;
    }

    public LocalDateTime getCreatedAt() 
    {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() 
    {
        return updatedAt;
    }

    public Integer getViewCount() 
    {
        return viewCount;
    }

    // Setter
    public void setId(Long id) 
    {
        this.id = id;
    }

    public void setTitle(String title) 
    {
        this.title = title;
    }

    public void setContent(String content) 
    {
        this.content = content;
    }

    public void setAuthor(String author) 
    {
        this.author = author;
    }

    public void setCategory(String category) 
    {
        this.category = category;
    }

    public void setIsImportant(Boolean isImportant) 
    {
        this.isImportant = isImportant;
    }

    public void setCreatedAt(LocalDateTime createdAt) 
    {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) 
    {
        this.updatedAt = updatedAt;
    }

    public void setViewCount(Integer viewCount) 
    {
        this.viewCount = viewCount;
    }

    @Override
    public String toString() 
    {
        return "NewsEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", isImportant=" + isImportant +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", viewCount=" + viewCount +
                '}';
    }
}