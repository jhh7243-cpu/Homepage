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
public class UserVO 
{
    // 타이타닉 승객 필드 (CSV 헤더 기반)
    private String userId;        // PassengerId (승객 ID)
    private String username;      // Name (승객 이름)
    private String password;      // Sex (성별)
    private String name;          // Age (나이)
    private String email;         // Survived (생존여부)
    private String phone;         // Ticket (티켓번호
    private String pclass;        // Pclass (승객 등급)
    private String sibSp;         // SibSp (형제자매/배우자 수)
    private String parch;         // Parch (부모/자녀 수)
    private String fare;          // Fare (요금)
    private String cabin;         // Cabin (객실 번호)
    private String embarked;      // Embarked (탑승 항구)

    
}
