package site.hohyun.auth.domain;

/**
 * 로그인 요청 도메인 모델
 * 로그인 시 필요한 정보를 담는 VO (Value Object)
 */
public class LoginRequest {
    private String email;
    private String password;

    // 기본 생성자
    public LoginRequest() {}

    // 생성자
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getter와 Setter
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
