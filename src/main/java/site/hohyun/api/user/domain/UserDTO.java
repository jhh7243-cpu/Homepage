package site.hohyun.api.user.domain;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 사용자 데이터 전송 객체 (Data Transfer Object)
 * 클라이언트와 서버 간 사용자 정보를 전송하기 위한 객체
 * CSV 데이터 처리를 위한 확장 기능 포함
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO 
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

    public UserDTO(String userId) 
    {
        this.userId = userId;
    }
    
    public UserDTO(String passengerId, String survived, String pclass, String name, 
                    String gender, String age, String ticket, String fare, 
                   String cabin, String embarked) 
    {
        this.passengerId = passengerId;
        this.survived = survived;
        this.pclass = pclass;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.ticket = ticket;
        this.fare = fare;
        this.cabin = cabin;
        this.embarked = embarked;
        this.userId = "passenger_" + passengerId;
    }

    public String getUserId() 
    {
        return userId;
    }

    public void setUserId(String userId) 
    {
        this.userId = userId;
    }

    // Getters and Setters
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
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
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
    public String toString() 
    {
        return "UserDTO{" +
                "userId='" + userId + '\'' +
                ", passengerId='" + passengerId + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", age='" + age + '\'' +
                ", survived='" + survived + '\'' +
                ", pclass='" + pclass + '\'' +
                ", ticket='" + ticket + '\'' +
                ", fare='" + fare + '\'' +
                ", cabin='" + cabin + '\'' +
                ", embarked='" + embarked + '\'' +
                '}';
    }
}
