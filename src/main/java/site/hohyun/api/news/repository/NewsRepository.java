package site.hohyun.api.news.repository;

import org.springframework.stereotype.Repository;
import site.hohyun.api.news.domain.NewsEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 공지사항 레포지토리
 * 데이터 접근 계층을 담당하는 레포지토리
 */
@Repository
public class NewsRepository 
{
    // 임시 데이터 저장소 (실제로는 DB를 사용)
    private final List<NewsEntity> newsList = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    // 초기 데이터 생성
    public NewsRepository() 
    {
        initializeData();
    }

    /**
     * 초기 데이터 생성
     */
    private void initializeData() 
    {
        LocalDateTime now = LocalDateTime.now();
        
        newsList.add(new NewsEntity(idGenerator.getAndIncrement(), 
                "시스템 점검 안내", 
                "더 나은 서비스 제공을 위해 시스템 점검을 실시합니다.\n\n점검 일시: 2024년 1월 15일 02:00 ~ 04:00\n점검 내용: 서버 업그레이드 및 보안 패치\n\n점검 시간 동안 서비스 이용이 제한될 수 있습니다.", 
                "관리자", 
                "시스템", 
                true, 
                now.minusDays(7), 
                now.minusDays(7), 
                156));
        
        newsList.add(new NewsEntity(idGenerator.getAndIncrement(), 
                "새로운 기능 업데이트", 
                "사용자 편의성 향상을 위한 새로운 기능들이 추가되었습니다.\n\n주요 업데이트 내용:\n- 게시판 UI 개선\n- 검색 기능 강화\n- 모바일 최적화\n\n많은 이용 부탁드립니다.", 
                "개발팀", 
                "업데이트", 
                false, 
                now.minusDays(5), 
                now.minusDays(5), 
                89));
        
        newsList.add(new NewsEntity(idGenerator.getAndIncrement(), 
                "이용약관 변경 안내", 
                "서비스 이용약관이 일부 변경되었습니다.\n\n변경 사항:\n- 개인정보 처리방침 업데이트\n- 서비스 이용 조건 추가\n- 고객지원 정책 변경\n\n자세한 내용은 이용약관을 확인해주세요.", 
                "법무팀", 
                "정책", 
                true, 
                now.minusDays(3), 
                now.minusDays(3), 
                67));
        
        newsList.add(new NewsEntity(idGenerator.getAndIncrement(), 
                "고객지원센터 운영시간 변경", 
                "고객지원센터 운영시간이 다음과 같이 변경됩니다.\n\n기존: 평일 09:00 ~ 18:00\n변경: 평일 09:00 ~ 19:00\n\n주말 및 공휴일은 휴무입니다.", 
                "고객지원팀", 
                "안내", 
                false, 
                now.minusDays(2), 
                now.minusDays(2), 
                34));
        
        newsList.add(new NewsEntity(idGenerator.getAndIncrement(), 
                "보안 강화 조치", 
                "계정 보안을 위한 추가 조치가 시행됩니다.\n\n보안 강화 내용:\n- 2단계 인증 권장\n- 정기적인 비밀번호 변경\n- 의심스러운 로그인 알림\n\n개인정보 보호를 위해 적극 협조해주세요.", 
                "보안팀", 
                "보안", 
                true, 
                now.minusDays(1), 
                now.minusDays(1), 
                123));
        
        newsList.add(new NewsEntity(idGenerator.getAndIncrement(), 
                "모바일 앱 출시", 
                "더욱 편리한 서비스 이용을 위한 모바일 앱이 출시되었습니다.\n\n앱 다운로드:\n- iOS: App Store\n- Android: Google Play Store\n\n모바일 앱에서만 제공되는 특별 기능들도 확인해보세요!", 
                "개발팀", 
                "앱", 
                false, 
                now.minusHours(12), 
                now.minusHours(12), 
                78));
    }

    /**
     * 모든 공지사항 조회
     */
    public List<NewsEntity> findAll() 
    {
        return new ArrayList<>(newsList);
    }

    /**
     * ID로 공지사항 조회
     */
    public Optional<NewsEntity> findById(Long id) 
    {
        return newsList.stream()
                .filter(news -> news.getId().equals(id))
                .findFirst();
    }

    /**
     * 공지사항 저장
     */
    public NewsEntity save(NewsEntity news) 
    {
        if (news.getId() == null) 
        {
            // 새 공지사항 생성
            news.setId(idGenerator.getAndIncrement());
            news.setCreatedAt(LocalDateTime.now());
            news.setUpdatedAt(LocalDateTime.now());
            news.setViewCount(0);
            newsList.add(news);
        } 
        else 
        {
            // 기존 공지사항 수정
            news.setUpdatedAt(LocalDateTime.now());
            newsList.replaceAll(n -> n.getId().equals(news.getId()) ? news : n);
        }
        return news;
    }

    /**
     * 공지사항 삭제
     */
    public boolean deleteById(Long id) 
    {
        return newsList.removeIf(news -> news.getId().equals(id));
    }

    /**
     * 조회수 증가
     */
    public void incrementViewCount(Long id) 
    {
        newsList.stream()
                .filter(news -> news.getId().equals(id))
                .findFirst()
                .ifPresent(news -> news.setViewCount(news.getViewCount() + 1));
    }

    /**
     * 중요 공지사항만 조회
     */
    public List<NewsEntity> findImportantNotices() 
    {
        return newsList.stream()
                .filter(news -> news.getIsImportant() != null && news.getIsImportant())
                .toList();
    }

    /**
     * 카테고리별 공지사항 조회
     */
    public List<NewsEntity> findByCategory(String category) 
    {
        return newsList.stream()
                .filter(news -> news.getCategory().equals(category))
                .toList();
    }

    /**
     * 제목으로 공지사항 검색
     */
    public List<NewsEntity> findByTitleContaining(String keyword) 
    {
        return newsList.stream()
                .filter(news -> news.getTitle().contains(keyword))
                .toList();
    }

    /**
     * 최신 공지사항 조회 (개수 제한)
     */
    public List<NewsEntity> findLatest(int limit) 
    {
        return newsList.stream()
                .sorted((n1, n2) -> n2.getCreatedAt().compareTo(n1.getCreatedAt()))
                .limit(limit)
                .toList();
    }

    /**
     * 공지사항 수 조회
     */
    public long count() 
    {
        return newsList.size();
    }
}