package site.hohyun.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 회원가입 관련 컨트롤러
 * 사용자 등록 기능을 처리
 */
@Controller
public class RegisterController 
{

    /**
     * 회원가입 페이지 표시
     * @return 회원가입 페이지 템플릿
     */
    @GetMapping("/register.html")
    public String registerPage() 
    {
        return "auth/register";
    }

    /**
     * 회원가입 처리
     * @param username 사용자명
     * @param email 이메일
     * @param password 비밀번호
     * @return 처리 결과
     */
    @PostMapping("/register")
    public String register(@RequestParam String username, 
                          @RequestParam String email, 
                          @RequestParam String password) 
    {
        // TODO: 회원가입 로직 구현
        System.out.println("회원가입 요청: " + username + ", " + email);
        return "redirect:/login.html";
    }
}
