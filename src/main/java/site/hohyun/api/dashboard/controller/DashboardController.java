package site.hohyun.api.dashboard.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import site.hohyun.api.dashboard.service.DashboardService;
import site.hohyun.api.dashboard.domain.DashboardDTO;
import lombok.RequiredArgsConstructor;

/**
 * 대시보드 관련 컨트롤러
 * 대시보드 페이지 표시 및 관련 기능을 처리
 */
@Controller
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController 
{
    private final DashboardService dashboardService;

    /**
     * 대시보드 페이지 표시 (로그인 후 메뉴 선택 페이지)
     * @param session HTTP 세션
     * @param model 뷰에 데이터 전달을 위한 모델
     * @return 대시보드 페이지 템플릿
     */
    @GetMapping("")
    public String dashboard(HttpSession session, Model model) 
    {
        // 세션에서 사용자 이름 가져오기
        String username = (String) session.getAttribute("username");
        
        // DashboardService를 통해 대시보드 데이터 조회
        DashboardDTO dashboardData = dashboardService.getDashboardData(username);
        
        model.addAttribute("username", username);
        model.addAttribute("dashboardData", dashboardData);
        
        return "dashboard/dashboard";
    }

    /**
     * 대시보드 페이지 표시 (HTML 직접 접근)
     * @param session HTTP 세션
     * @param model 뷰에 데이터 전달을 위한 모델
     * @return 대시보드 페이지 템플릿
     */
    @GetMapping("/dashboard.html")
    public String dashboardHtml(HttpSession session, Model model) 
    {
        return dashboard(session, model);
    }
}
