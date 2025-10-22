package site.hohyun.api.board.domain;

import java.time.LocalDateTime;

/**
 * 게시판 값 객체
 * 불변 객체로 사용되는 값들의 집합
 */
public class BoardVO 
{
    private final Long id;
    private final String title;
    private final String content;
    private final String author;
    private final String category;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final Integer viewCount;

    // 생성자
    public BoardVO(Long id, String title, String content, String author, 
                   String category, LocalDateTime createdAt, LocalDateTime updatedAt, Integer viewCount) 
    {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.viewCount = viewCount;
    }

    // Entity에서 VO로 변환하는 생성자
    public BoardVO(BoardEntity entity) 
    {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
        this.category = entity.getCategory();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
        this.viewCount = entity.getViewCount();
    }

    // DTO에서 VO로 변환하는 생성자
    public BoardVO(BoardDTO dto) 
    {
        this.id = dto.getId();
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.author = dto.getAuthor();
        this.category = dto.getCategory();
        this.createdAt = dto.getCreatedAt();
        this.updatedAt = dto.getUpdatedAt();
        this.viewCount = dto.getViewCount();
    }

    // Getter (불변 객체이므로 setter 없음)
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

    // 날짜 포맷팅 메서드
    public String getFormattedCreatedAt() 
    {
        return createdAt != null ? createdAt.toString().substring(0, 10) : "";
    }

    public String getFormattedUpdatedAt() 
    {
        return updatedAt != null ? updatedAt.toString().substring(0, 10) : "";
    }

    @Override
    public String toString() 
    {
        return "BoardVO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", viewCount=" + viewCount +
                '}';
    }
}