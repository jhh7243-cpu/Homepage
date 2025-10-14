package site.hohyun.auth.domain;

/**
 * 사용자 도메인 모델
 * 사용자 정보를 나타내는 엔티티
 */
public class User {
    private Long id;
    private String username;
    private String email;
    private String password;
    private boolean isActive;

    // 기본 생성자
    public User() {}

    // 생성자
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.isActive = true;
    }

    // Getter와 Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
