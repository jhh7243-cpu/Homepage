package site.hohyun.api.dashboard.domain;

import lombok.Data;
import java.util.List;

/**
 * 대시보드 데이터 전송 객체
 * 대시보드 관련 데이터를 전송하기 위한 DTO
 */
@Data
public class DashboardDTO 
{
    private String username;
    private String welcomeMessage;
    private int totalGames;
    private int totalTools;
    private List<String> availableGames;
    private List<String> availableTools;
    private String lastLoginTime;
    private String userRole;
}
