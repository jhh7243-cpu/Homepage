package site.hohyun.api.news.service;

import org.springframework.stereotype.Service;
import site.hohyun.api.news.domain.NewsDTO;
import site.hohyun.api.news.domain.NewsEntity;
import site.hohyun.api.news.domain.NewsVO;
import site.hohyun.api.news.repository.NewsRepository;
import site.hohyun.api.common.domain.Messenger;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 공지사항 서비스
 * 비즈니스 로직을 담당하는 서비스 계층
 */
@Service
public class NewsService 
{
    private final NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository) 
    {
        this.newsRepository = newsRepository;
    }

    /**
     * 모든 공지사항 조회 (getAllNotices는 기존 컨트롤러와 호환성을 위해 유지)
     */
    public List<NewsVO> getAllNotices() 
    {
        System.out.println("공지사항 서비스: 모든 공지사항 조회");
        return newsRepository.findAll().stream()
                .map(NewsVO::new)
                .collect(Collectors.toList());
    }

    /**
     * 모든 공지사항 조회
     */
    public List<NewsVO> getAllNews() 
    {
        System.out.println("공지사항 서비스: 모든 공지사항 조회");
        return newsRepository.findAll().stream()
                .map(NewsVO::new)
                .collect(Collectors.toList());
    }

    /**
     * ID로 공지사항 조회
     */
    public Optional<NewsVO> getNoticeById(Long id) 
    {
        System.out.println("공지사항 서비스: ID로 공지사항 조회 - " + id);
        return newsRepository.findById(id)
                .map(NewsVO::new);
    }

    /**
     * 공지사항 생성
     */
    public Messenger createNews(NewsDTO newsDTO) 
    {
        System.out.println("공지사항 서비스: 공지사항 생성 - " + newsDTO.getTitle());
        
        Messenger messenger = new Messenger();
        
        try 
        {
            // 유효성 검사
            if (newsDTO.getTitle() == null || newsDTO.getTitle().trim().isEmpty()) 
            {
                messenger.setCode(1);
                messenger.setMessage("제목을 입력해주세요.");
                return messenger;
            }
            
            if (newsDTO.getContent() == null || newsDTO.getContent().trim().isEmpty()) 
            {
                messenger.setCode(2);
                messenger.setMessage("내용을 입력해주세요.");
                return messenger;
            }
            
            if (newsDTO.getAuthor() == null || newsDTO.getAuthor().trim().isEmpty()) 
            {
                messenger.setCode(3);
                messenger.setMessage("작성자를 입력해주세요.");
                return messenger;
            }

            // DTO를 Entity로 변환하여 저장
            NewsEntity entity = newsDTO.toEntity();
            NewsEntity savedEntity = newsRepository.save(entity);
            
            messenger.setCode(0);
            messenger.setMessage("공지사항이 성공적으로 작성되었습니다. (ID: " + savedEntity.getId() + ")");
            
        } 
        catch (Exception e) 
        {
            System.err.println("공지사항 생성 오류: " + e.getMessage());
            messenger.setCode(999);
            messenger.setMessage("공지사항 작성 중 오류가 발생했습니다: " + e.getMessage());
        }
        
        return messenger;
    }

    /**
     * 공지사항 수정
     */
    public Messenger updateNews(Long id, NewsDTO newsDTO) 
    {
        System.out.println("공지사항 서비스: 공지사항 수정 - ID: " + id);
        
        Messenger messenger = new Messenger();
        
        try 
        {
            Optional<NewsEntity> existingNews = newsRepository.findById(id);
            
            if (existingNews.isEmpty()) 
            {
                messenger.setCode(404);
                messenger.setMessage("해당 공지사항을 찾을 수 없습니다.");
                return messenger;
            }
            
            // 유효성 검사
            if (newsDTO.getTitle() == null || newsDTO.getTitle().trim().isEmpty()) 
            {
                messenger.setCode(1);
                messenger.setMessage("제목을 입력해주세요.");
                return messenger;
            }
            
            if (newsDTO.getContent() == null || newsDTO.getContent().trim().isEmpty()) 
            {
                messenger.setCode(2);
                messenger.setMessage("내용을 입력해주세요.");
                return messenger;
            }

            // 기존 공지사항 정보 유지하면서 수정
            NewsEntity entity = existingNews.get();
            entity.setTitle(newsDTO.getTitle());
            entity.setContent(newsDTO.getContent());
            entity.setCategory(newsDTO.getCategory());
            entity.setIsImportant(newsDTO.getIsImportant());
            
            newsRepository.save(entity);
            
            messenger.setCode(0);
            messenger.setMessage("공지사항이 성공적으로 수정되었습니다.");
            
        } 
        catch (Exception e) 
        {
            System.err.println("공지사항 수정 오류: " + e.getMessage());
            messenger.setCode(999);
            messenger.setMessage("공지사항 수정 중 오류가 발생했습니다: " + e.getMessage());
        }
        
        return messenger;
    }

    /**
     * 공지사항 삭제
     */
    public Messenger deleteNews(Long id) 
    {
        System.out.println("공지사항 서비스: 공지사항 삭제 - ID: " + id);
        
        Messenger messenger = new Messenger();
        
        try 
        {
            boolean deleted = newsRepository.deleteById(id);
            
            if (deleted) 
            {
                messenger.setCode(0);
                messenger.setMessage("공지사항이 성공적으로 삭제되었습니다.");
            } 
            else 
            {
                messenger.setCode(404);
                messenger.setMessage("해당 공지사항을 찾을 수 없습니다.");
            }
            
        } 
        catch (Exception e) 
        {
            System.err.println("공지사항 삭제 오류: " + e.getMessage());
            messenger.setCode(999);
            messenger.setMessage("공지사항 삭제 중 오류가 발생했습니다: " + e.getMessage());
        }
        
        return messenger;
    }

    /**
     * 조회수 증가
     */
    public void incrementViewCount(Long id) 
    {
        System.out.println("공지사항 서비스: 조회수 증가 - ID: " + id);
        newsRepository.incrementViewCount(id);
    }

    /**
     * 중요 공지사항 조회
     */
    public List<NewsVO> getImportantNotices() 
    {
        System.out.println("공지사항 서비스: 중요 공지사항 조회");
        return newsRepository.findImportantNotices().stream()
                .map(NewsVO::new)
                .collect(Collectors.toList());
    }

    /**
     * 카테고리별 공지사항 조회
     */
    public List<NewsVO> getNewsByCategory(String category) 
    {
        System.out.println("공지사항 서비스: 카테고리별 공지사항 조회 - " + category);
        return newsRepository.findByCategory(category).stream()
                .map(NewsVO::new)
                .collect(Collectors.toList());
    }

    /**
     * 제목으로 공지사항 검색
     */
    public List<NewsVO> searchNewsByTitle(String keyword) 
    {
        System.out.println("공지사항 서비스: 제목으로 공지사항 검색 - " + keyword);
        return newsRepository.findByTitleContaining(keyword).stream()
                .map(NewsVO::new)
                .collect(Collectors.toList());
    }

    /**
     * 최신 공지사항 조회
     */
    public List<NewsVO> getLatestNews(int limit) 
    {
        System.out.println("공지사항 서비스: 최신 공지사항 조회 - " + limit + "개");
        return newsRepository.findLatest(limit).stream()
                .map(NewsVO::new)
                .collect(Collectors.toList());
    }

    /**
     * 공지사항 수 조회
     */
    public long getNewsCount() 
    {
        return newsRepository.count();
    }
}