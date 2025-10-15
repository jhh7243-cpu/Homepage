package site.hohyun.api.news.service;

import org.springframework.stereotype.Service;
import site.hohyun.api.news.domain.NoticeDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * 공지사항 서비스
 * 공지사항 비즈니스 로직 처리
 */
@Service
public class NewsService 
{
    /**
     * 모든 공지사항 조회
     * @return 공지사항 목록
     */
    public List<NoticeDTO> getAllNotices()
    {
        System.out.println("공지사항 서비스로 들어옴 - 전체 목록 조회");
        
        List<NoticeDTO> notices = new ArrayList<>();
        
        // 샘플 공지사항 데이터
        notices.add(new NoticeDTO(1L, "🚨 중요 공지: 시스템 점검 안내", "중요", 
            "2024년 1월 25일 오전 2시부터 6시까지 시스템 점검이 진행됩니다.", 
            "관리자", "2024-01-20", 2847));
            
        notices.add(new NoticeDTO(2L, "✨ GameHub v2.0 업데이트 완료", "업데이트", 
            "새로운 UI/UX 디자인과 계산기 성능 개선이 적용되었습니다.", 
            "관리자", "2024-01-18", 1923));
            
        notices.add(new NoticeDTO(3L, "🎉 신규 사용자 환영 이벤트", "이벤트", 
            "GameHub에 새로 가입하신 모든 사용자분들께 특별한 혜택을 제공합니다.", 
            "관리자", "2024-01-15", 3156));
            
        notices.add(new NoticeDTO(4L, "📱 모바일 앱 출시 예정", "소식", 
            "GameHub 모바일 앱이 곧 출시됩니다. 언제 어디서나 편리하게 이용하실 수 있습니다.", 
            "관리자", "2024-01-12", 1456));
            
        notices.add(new NoticeDTO(5L, "🔧 계산기 기능 개선 사항", "개선", 
            "계산기 성능이 대폭 개선되었습니다. 더욱 정확하고 빠른 계산 결과를 제공합니다.", 
            "관리자", "2024-01-10", 987));
        
        System.out.println("공지사항 " + notices.size() + "개 조회 완료");
        return notices;
    }

    /**
     * ID로 공지사항 조회
     * @param id 공지사항 ID
     * @return 공지사항 정보
     */
    public NoticeDTO getNoticeById(Long id)
    {
        System.out.println("공지사항 서비스로 들어옴 - ID로 조회: " + id);
        
        // 실제로는 데이터베이스에서 조회
        // 여기서는 샘플 데이터 반환
        return new NoticeDTO(id, "샘플 공지사항", "일반", 
            "이것은 샘플 공지사항입니다.", "관리자", "2024-01-01", 100);
    }
}
