package site.hohyun.api.user.domain;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * 사용자 값 객체 (Value Object)
 * 사용자 정보를 담는 읽기 전용 객체
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {
    private String userId;
    private String name;
    private String email;
    private String phone;


    public String getUserId() 
    {
        return userId;
    }

    public String getName() 
    {
        return name;
    }

    public String getEmail() 
    {
        return email;
    }

    public String getPhone() 
    {
        return phone;
    }

    @Override
    public String toString() 
    {
        return "UserVO{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
