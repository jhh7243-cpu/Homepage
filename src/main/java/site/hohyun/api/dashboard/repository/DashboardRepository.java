package site.hohyun.api.dashboard.repository;

import org.springframework.stereotype.Repository;
import site.hohyun.api.dashboard.domain.DashboardDTO;
import java.util.Arrays;
import java.util.List;

/**
 * 대시보드 데이터 접근 레포지토리
 * 대시보드 관련 데이터 조회 및 저장을 담당
 */
@Repository
public class DashboardRepository 
{
    /**
     * 사용 가능한 게임 목록 조회
     * @return 게임 목록
     */
    public List<String> getAvailableGames() 
    {
        return Arrays.asList(
            "테트리스",
            "게시판"
        );
    }

    /**
     * 사용 가능한 도구 목록 조회
     * @return 도구 목록
     */
    public List<String> getAvailableTools() 
    {
        return Arrays.asList(
            "계산기",
            "공지사항"
        );
    }

    /**
     * 사용자 대시보드 통계 조회
     * @param username 사용자 이름
     * @return 대시보드 통계 데이터
     */
    public DashboardDTO getUserDashboardStats(String username) 
    {
        DashboardDTO stats = new DashboardDTO();
        stats.setUsername(username);
        stats.setWelcomeMessage("환영합니다, " + username + "님!");
        
        // 실제 구현에서는 데이터베이스에서 통계 데이터를 조회
        // 현재는 기본값으로 설정
        stats.setTotalGames(2);
        stats.setTotalTools(2);
        stats.setAvailableGames(getAvailableGames());
        stats.setAvailableTools(getAvailableTools());
        
        return stats;
    }
}
