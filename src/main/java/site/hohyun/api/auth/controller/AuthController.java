package site.hohyun.api.auth.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import site.hohyun.api.auth.domain.LoginDTO;
import site.hohyun.api.auth.service.LoginService;

/**
 * 인증 관련 컨트롤러
 * 로그인, 회원가입 등 인증 관련 기능을 처리
 */

@Controller
public class AuthController 
{
    private final LoginService loginService;
    
    public AuthController(LoginService loginService)
    {
        this.loginService = loginService;
    }
    
    /**
     * 로그인 처리
     * @param email 사용자 이메일
     * @param password 사용자 비밀번호
     * @param session HTTP 세션
     * @param model 모델 객체
     * @return 대시보드 페이지로 리다이렉트 또는 로그인 페이지
     */
    @PostMapping("/login")
    public String login(@RequestParam("email") String email, 
                       @RequestParam("password") String password,
                       HttpSession session,
                       Model model) 
    {
        System.out.println("컨트롤러로 들어옴");
        System.out.println("화면에서 컨트롤러로 전달된 이메일 = " + email);
        System.out.println("화면에서 컨트롤러로 전달된 비밀번호 = " + password);
        
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail(email);
        loginDTO.setPassword(password);

        boolean loginSuccess = loginService.login(loginDTO);
        
        if (loginSuccess) 
        {
            // 로그인 성공 시 세션에 사용자 정보 저장
            session.setAttribute("username", email);
            session.setAttribute("isLoggedIn", true);
            System.out.println("로그인 성공! 대시보드로 이동");
            return "redirect:/dashboard.html";
        } 
        else 
        {
            // 로그인 실패 시 에러 메시지와 함께 로그인 페이지로
            model.addAttribute("error", "이메일 또는 비밀번호가 올바르지 않습니다.");
            System.out.println("로그인 실패!");
            return "auth/login";
        }
    }
}
