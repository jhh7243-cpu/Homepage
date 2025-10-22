package site.hohyun.api.user.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import site.hohyun.api.user.userrepository.UserRepository;
import site.hohyun.api.user.domain.UserDTO;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService 
{
    private final UserRepository userRepository;

    public UserDTO registerUser(UserDTO userDTO) {
        System.out.println("=== UserService.registerUser() 호출됨 ===");
        System.out.println("Service에서 받은 DTO: " + userDTO.toString());
        
        // Repository에 DTO 직접 저장
        UserDTO savedDTO = userRepository.save(userDTO);
        System.out.println("Service에서 Repository 저장 완료!");
        
        // Repository 디버깅 정보 출력
        System.out.println("=== Service에서 Repository 상태 확인 ===");
        userRepository.printAllDTOs();
        System.out.println("현재 Repository에 저장된 총 DTO 수: " + userRepository.getDTOCount());
        System.out.println("=== Service 디버깅 정보 끝 ===");
        
        return savedDTO;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll();
    }
    
    /**
     * 사용자명으로 사용자 찾기
     */
    public UserDTO findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    /**
     * 이메일로 사용자 찾기
     */
    public UserDTO findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    /**
     * 사용자 ID로 사용자 찾기
     */
    public UserDTO findById(String userId) {
        return userRepository.findById(userId);
    }
    
    /**
     * 승객 명단을 터미널에 출력
     */
    public void printPassengerList(List<UserDTO> passengers) {
        userRepository.printPassengerList(passengers);
    }
}
   