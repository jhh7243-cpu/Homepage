package site.hohyun.api.dashboard.domain;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 대시보드 뷰 객체
 * 대시보드 화면에 표시할 데이터를 담는 VO
 */
@Data
public class DashboardVO 
{
    private String username;
    private String welcomeMessage;
    private int totalGames;
    private int totalTools;
    private List<String> availableGames;
    private List<String> availableTools;
    private LocalDateTime lastLoginTime;
    private String userRole;
    private boolean isAdmin;
    private String dashboardTitle;
    private String dashboardSubtitle;
}