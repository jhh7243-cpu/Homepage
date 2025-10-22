package site.hohyun.api.dashboard.service;

import org.springframework.stereotype.Service;
import site.hohyun.api.dashboard.domain.DashboardDTO;
import site.hohyun.api.dashboard.repository.DashboardRepository;
import lombok.RequiredArgsConstructor;

/**
 * 대시보드 비즈니스 로직 서비스
 * 대시보드 관련 비즈니스 로직을 처리
 */
@Service
@RequiredArgsConstructor
public class DashboardService 
{
    private final DashboardRepository dashboardRepository;

    /**
     * 대시보드 데이터 조회
     * @param username 사용자 이름
     * @return 대시보드 데이터 DTO
     */
    public DashboardDTO getDashboardData(String username) 
    {
        // 대시보드 데이터 조회 로직
        DashboardDTO dashboardData = new DashboardDTO();
        dashboardData.setUsername(username);
        dashboardData.setWelcomeMessage("환영합니다, " + username + "님!");
        
        // 사용 가능한 게임/도구 목록 설정
        dashboardData.setAvailableGames(dashboardRepository.getAvailableGames());
        dashboardData.setAvailableTools(dashboardRepository.getAvailableTools());
        
        return dashboardData;
    }

    /**
     * 사용자 대시보드 통계 조회
     * @param username 사용자 이름
     * @return 대시보드 통계 데이터
     */
    public DashboardDTO getUserDashboardStats(String username) 
    {
        return dashboardRepository.getUserDashboardStats(username);
    }
}
