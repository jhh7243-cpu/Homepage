package site.hohyun.api.user.domain;

/**
 * 사용자 엔티티 클래스
 * 디버깅을 위한 기본 구현
 */
public class UserEntity 
{
    private String userId;
    private String username;
    private String password;
    private String name;
    private String gender;
    private String age;
    private String email;
    private String phone;
    private String passengerId;
    private String survived;
    private String pclass;
    private String ticket;
    private String fare;
    private String cabin;
    private String embarked;

    // 기본 생성자
    public UserEntity() {}

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    
    public String getAge() { return age; }
    public void setAge(String age) { this.age = age; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getPassengerId() { return passengerId; }
    public void setPassengerId(String passengerId) { this.passengerId = passengerId; }
    
    public String getSurvived() { return survived; }
    public void setSurvived(String survived) { this.survived = survived; }
    
    public String getPclass() { return pclass; }
    public void setPclass(String pclass) { this.pclass = pclass; }
    
    public String getTicket() { return ticket; }
    public void setTicket(String ticket) { this.ticket = ticket; }
    
    public String getFare() { return fare; }
    public void setFare(String fare) { this.fare = fare; }
    
    public String getCabin() { return cabin; }
    public void setCabin(String cabin) { this.cabin = cabin; }
    
    public String getEmbarked() { return embarked; }
    public void setEmbarked(String embarked) { this.embarked = embarked; }

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", passengerId='" + passengerId + '\'' +
                ", survived='" + survived + '\'' +
                ", pclass='" + pclass + '\'' +
                '}';
    }
}
