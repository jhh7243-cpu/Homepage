package site.hohyun.api.home.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 홈페이지 관련 컨트롤러
 * 메인 페이지, 대시보드 등 홈 관련 기능을 처리
 */
@Controller
public class HomeController 
{

    /**
     * 메인 홈페이지 표시
     * @return 메인 페이지 템플릿
     */
    @GetMapping("/")
    public String index() 
    {
        return "index"; // resolves to templates/index.html via Thymeleaf
    }

    /**
     * 메인 홈페이지 표시 (index.html 직접 접근)
     * @return 메인 페이지 템플릿
     */
    @GetMapping("/index.html")
    public String indexHtml() 
    {
        return "index";
    }

    /**
     * 로그인 페이지 표시
     * @return 로그인 페이지 템플릿
     */
    @GetMapping("/login.html")
    public String loginPage() 
    {
        return "auth/login";
    }

    /**
     * 대시보드 페이지 표시 (로그인 후 메뉴 선택 페이지)
     * @param session HTTP 세션
     * @param model 뷰에 데이터 전달을 위한 모델
     * @return 대시보드 페이지 템플릿
     */
    @GetMapping("/dashboard.html")
    public String dashboard(HttpSession session, Model model) 
    {
        // 세션에서 사용자 이름 가져오기
        String username = (String) session.getAttribute("username");
        model.addAttribute("username", username);
        return "dashboard"; // resolves to templates/dashboard.html
    }
    
    /**
     * 계산기 페이지 표시
     * @return 계산기 페이지 템플릿
     */
    @GetMapping("/calculator.html")
    public String calculator() 
    {
        return "calculator/calculator"; // resolves to templates/calculator/calculator.html
    }

    /**
     * 공지사항 페이지 표시
     * @return 공지사항 페이지 템플릿
     */
    @GetMapping("/news.html")
    public String news() 
    {
        return "news/news"; // resolves to templates/news/news.html
    }

    /**
     * 로그아웃 처리
     * @param session HTTP 세션
     * @return 메인 페이지로 리다이렉트
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) 
    {
        // 세션 무효화
        session.invalidate();
        System.out.println("로그아웃 완료");
        return "redirect:/";
    }
}
