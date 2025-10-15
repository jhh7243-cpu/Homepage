package site.hohyun.api.news.domain;

/**
 * 공지사항 데이터 전송 객체
 * 공지사항 정보를 전달하기 위한 DTO
 */
public class NoticeDTO 
{
    private Long id;
    private String title;
    private String category;
    private String content;
    private String author;
    private String createdDate;
    private int viewCount;

    // 기본 생성자
    public NoticeDTO() 
    {
    }

    // 전체 생성자
    public NoticeDTO(Long id, String title, String category, String content, 
                    String author, String createdDate, int viewCount) 
    {
        this.id = id;
        this.title = title;
        this.category = category;
        this.content = content;
        this.author = author;
        this.createdDate = createdDate;
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

    public String getCategory() 
    {
        return category;
    }

    public String getContent() 
    {
        return content;
    }

    public String getAuthor() 
    {
        return author;
    }

    public String getCreatedDate() 
    {
        return createdDate;
    }

    public int getViewCount() 
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

    public void setCategory(String category) 
    {
        this.category = category;
    }

    public void setContent(String content) 
    {
        this.content = content;
    }

    public void setAuthor(String author) 
    {
        this.author = author;
    }

    public void setCreatedDate(String createdDate) 
    {
        this.createdDate = createdDate;
    }

    public void setViewCount(int viewCount) 
    {
        this.viewCount = viewCount;
    }

    @Override
    public String toString() 
    {
        return "NoticeDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", viewCount=" + viewCount +
                '}';
    }
}
