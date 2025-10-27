package site.hohyun.api.user.userrepository;

import org.springframework.stereotype.Repository;
import site.hohyun.api.user.domain.UserDTO;
import site.hohyun.api.common.domain.Messenger;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 사용자 데이터 저장소
 */
@Repository
public class UserRepository 
{
    // 실제 데이터를 저장할 Map (메모리 저장소)
    private final Map<String, UserDTO> userStorage = new ConcurrentHashMap<>();

    /**
     * 사용자 데이터 저장
     */
    public UserDTO save(UserDTO userDTO) 
    {
        String key = userDTO.getUserId();
        if (key == null || key.isEmpty()) 
        {
            key = "user_" + System.currentTimeMillis();
        }
        
        userStorage.put(key, userDTO);
        
        return userDTO;
    }
    
    /**
     * 데이터 저장 상태 확인 (Messenger 반환)
     */
    public Messenger checkDataStatus() 
    {
        int totalCount = userStorage.size();
        String message;
        int code;
        
        if (totalCount == 0) {
            message = "📭 저장된 타이타닉 승객 데이터가 없습니다.";
            code = 404;
        } else if (totalCount == 5) {
            message = "🎉 타이타닉 승객 5명 데이터가 성공적으로 저장되었습니다!";
            code = 200;
        } else {
            message = "📊 현재 " + totalCount + "명의 타이타닉 승객 데이터가 저장되어 있습니다.";
            code = 201;
        }
        
        return new Messenger(code, message);
    }
    
    /**
     * 사용자 데이터 저장 (Messenger 반환)
     */
    public Messenger saveWithStatus(UserDTO userDTO) 
    {
        try {
            String key = userDTO.getUserId();
            if (key == null || key.isEmpty()) 
            {
                key = "user_" + System.currentTimeMillis();
            }
            
            userStorage.put(key, userDTO);
            
            return new Messenger(200, "사용자 데이터가 성공적으로 저장되었습니다.");
        } catch (Exception e) {
            return new Messenger(500, "사용자 데이터 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    /**
     * 모든 사용자 데이터 조회 (Messenger 반환)
     */
    public Messenger findAllWithStatus() 
    {
        try {
            List<UserDTO> userList = new ArrayList<>(userStorage.values());
            
            if (userList.isEmpty()) {
                return new Messenger(404, "저장된 사용자 데이터가 없습니다.");
            } else {
                return new Messenger(200, "총 " + userList.size() + "명의 사용자 데이터를 조회했습니다.");
            }
        } catch (Exception e) {
            return new Messenger(500, "사용자 데이터 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    /**
     * 사용자 데이터 삭제 (Messenger 반환)
     */
    public Messenger deleteWithStatus(String userId) 
    {
        try {
            UserDTO removed = userStorage.remove(userId);
            if (removed != null) {
                return new Messenger(200, "사용자 데이터가 성공적으로 삭제되었습니다.");
            } else {
                return new Messenger(404, "삭제할 사용자 데이터를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            return new Messenger(500, "사용자 데이터 삭제 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    /**
     * 사용자 데이터 업데이트 (Messenger 반환)
     */
    public Messenger updateWithStatus(UserDTO userDTO) 
    {
        try {
            String key = userDTO.getUserId();
            if (userStorage.containsKey(key)) {
                userStorage.put(key, userDTO);
                return new Messenger(200, "사용자 데이터가 성공적으로 업데이트되었습니다.");
            } else {
                return new Messenger(404, "업데이트할 사용자 데이터를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            return new Messenger(500, "사용자 데이터 업데이트 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    /**
     * 모든 사용자 데이터 조회
     */
    public List<UserDTO> findAll() 
    {
        List<UserDTO> userList = new ArrayList<>(userStorage.values());
        
        return userList;
    }
    
    /**
     * 사용자 ID로 조회
     */
    public UserDTO findById(String userId) 
    {
        UserDTO found = userStorage.get(userId);
        
        return found;
    }
    
    /**
     * 사용자명으로 조회
     */
    public UserDTO findByUsername(String username) 
    {
        for (UserDTO user : userStorage.values()) 
        {
            if (username.equals(user.getUsername())) 
            {
                return user;
            }
        }
        
        return null;
    }
    
    /**
     * 이메일로 조회
     */
    public UserDTO findByEmail(String email) 
    {
        for (UserDTO user : userStorage.values()) 
        {
            if (email.equals(user.getEmail())) 
            {
                return user;
            }
        }
        
        return null;
    }
}