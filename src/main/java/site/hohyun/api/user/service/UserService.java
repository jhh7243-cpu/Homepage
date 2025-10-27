package site.hohyun.api.user.service;

import org.springframework.stereotype.Service;
import site.hohyun.api.common.domain.Messenger;
import site.hohyun.api.user.domain.UserDTO;
import java.util.List;

@Service
public interface UserService 
{
    Messenger save(UserDTO userDTO);
    Messenger saveAll(List<UserDTO> userDTOList);
    Messenger update(UserDTO userDTO);
    Messenger delete(UserDTO userDTO);
    Messenger findbyId(UserDTO userDTO);
    Messenger findbyall(UserDTO userDTO);
    Messenger checkDataStatus(UserDTO userDTO);
    List<UserDTO> getAllUsers();
    UserDTO findByEmail(String email);
    
    // Messenger 반환 메서드들 (Repository의 Messenger 메서드 활용)
    Messenger saveWithStatus(UserDTO userDTO);
    Messenger deleteWithStatus(UserDTO userDTO);
    Messenger updateWithStatus(UserDTO userDTO);
    Messenger getAllUsersWithStatus();
    Messenger checkUserDataStatus();
}