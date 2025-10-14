package site.hohyun.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 인증 관련 컨트롤러
 * 로그인, 회원가입 등 인증 관련 기능을 처리
 */
@Controller
public class AuthController {

    /**
     * 로그인 처리
     * @param email 사용자 이메일
     * @param password 사용자 비밀번호
     * @return 로그인 페이지 템플릿
     */
    @GetMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        System.out.println("email = " + email);
        System.out.println("password = " + password);
        return "auth/login";
    }
}
