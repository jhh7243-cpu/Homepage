package site.hohyun.api.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;

import site.hohyun.api.user.service.UserService;
import site.hohyun.api.user.domain.UserDTO;

/**
 * 인증 관련 컨트롤러
 * 로그인, 회원가입 등 인증 관련 기능을 처리
 */

@Controller
@RequestMapping("/auth")
public class LoginController 
{
    private final UserService userService;

    public LoginController(UserService userService) 
    {
        this.userService = userService;
    }

    /**
     * 로그인 페이지 표시
     * 
     * @param model 모델 객체
     * @return 로그인 페이지
     */
    @GetMapping("/login")
    public String loginPage(Model model) 
    {
        return "auth/login";
    }

    /**
     * 로그인 처리
     * 
     * @param username 사용자명 또는 이메일
     * @param password 사용자 비밀번호
     * @param model    모델 객체
     * @return 대시보드 페이지로 리다이렉트 또는 로그인 페이지
     */
    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
            @RequestParam("password") String password, Model model, HttpSession session) 
    {
        try {
            // 디버깅을 위한 로그 출력
            System.out.println("=== 로그인 시도 ===");
            System.out.println("입력된 이메일: " + username);
            System.out.println("입력된 비밀번호: " + password);
            
            // 테스트용 사용자 생성 (A@test.com / 1234)
            if (username.equals("A@test.com") && password.equals("1234")) {
                // 테스트 사용자가 존재하는지 확인
                UserDTO user = userService.findByEmail(username);
                
                if (user == null) {
                    // 테스트 사용자가 없으면 생성
                    System.out.println("테스트 사용자 생성 중...");
                    UserDTO testUser = new UserDTO();
                    testUser.setUserId("test_user_1");
                    testUser.setUsername("testuser");
                    testUser.setPassword("1234");
                    testUser.setEmail("A@test.com");
                    testUser.setName("Test User");
                    
                    // 사용자 저장
                    userService.registerUser(testUser);
                    System.out.println("테스트 사용자 생성 완료!");
                    user = testUser;
                }
                
                // 로그인 성공
                System.out.println("테스트 사용자 로그인 성공!");
                session.setAttribute("isLoggedIn", true);
                session.setAttribute("username", user.getUsername());
                session.setAttribute("email", user.getEmail());
                System.out.println("세션에 로그인 정보 저장 완료: " + user.getUsername());
                model.addAttribute("message", "로그인에 성공했습니다!");
                return "redirect:/dashboard";
            }
            
            // 일반 사용자 인증
            UserDTO user = userService.findByEmail(username);
            
            if (user != null) {
                System.out.println("사용자 찾음: " + user.getUsername());
                // 실제 비밀번호 검증 로직 (현재는 임시로 간단한 검증)
                // TODO: 실제 비밀번호 해시 검증 로직 구현 필요
                if (user.getPassword().equals(password)) {
                    // 로그인 성공 - 세션에 로그인 정보 저장
                    System.out.println("로그인 성공!");
                    session.setAttribute("isLoggedIn", true);
                    session.setAttribute("username", user.getUsername());
                    session.setAttribute("email", user.getEmail());
                    System.out.println("세션에 로그인 정보 저장 완료: " + user.getUsername());
                    model.addAttribute("message", "로그인에 성공했습니다!");
                    return "redirect:/dashboard";
                } else {
                    // 비밀번호 불일치
                    System.out.println("로그인 실패 - 비밀번호 불일치");
                    model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
                    return "auth/login";
                }
            } else {
                // 사용자를 찾을 수 없음
                System.out.println("로그인 실패 - 사용자를 찾을 수 없음");
                model.addAttribute("error", "이메일 또는 비밀번호가 일치하지 않습니다.");
                return "auth/login";
            }
            
        } catch (Exception e) {
            System.err.println("로그인 오류: " + e.getMessage());
            model.addAttribute("error", "로그인 중 오류가 발생했습니다: " + e.getMessage());
            return "auth/login";
        }
    }
}