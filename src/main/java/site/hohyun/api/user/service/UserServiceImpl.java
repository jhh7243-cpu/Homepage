package site.hohyun.api.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import site.hohyun.api.user.userrepository.UserRepository;
import site.hohyun.api.common.domain.Messenger;
import site.hohyun.api.user.domain.UserDTO;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService 
{
    private final UserRepository userRepository;

    @Override
    public Messenger save(UserDTO userDTO) {
        try {
            userRepository.save(userDTO);
            return new Messenger(200, "사용자가 성공적으로 저장되었습니다.");
        } catch (Exception e) {
            return new Messenger(500, "사용자 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @Override
    public Messenger saveAll(List<UserDTO> userDTOList) {
        try {
            for (UserDTO userDTO : userDTOList) {
                userRepository.save(userDTO);
            }
            return new Messenger(200, "총 " + userDTOList.size() + "명의 사용자가 성공적으로 저장되었습니다.");
        } catch (Exception e) {
            return new Messenger(500, "사용자 목록 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @Override
    public Messenger update(UserDTO userDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public Messenger delete(UserDTO userDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public Messenger findbyId(UserDTO userDTO) {
        try {
            // 이메일로 사용자 찾기 시도
            UserDTO foundUser = userRepository.findByEmail(userDTO.getEmail());
            if (foundUser != null) {
                return new Messenger(200, "사용자를 찾았습니다.");
            } else {
                return new Messenger(404, "사용자를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            return new Messenger(500, "사용자 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @Override
    public Messenger findbyall(UserDTO userDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findbyall'");
    }

    @Override
    public Messenger checkDataStatus(UserDTO userDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkDataStatus'");
    }

    @Override
    public List<UserDTO> getAllUsers() {
        try {
            List<UserDTO> userList = userRepository.findAll();
            return userList;
        } catch (Exception e) {
            System.err.println("❌ UserService: 사용자 목록 조회 중 오류: " + e.getMessage());
            e.printStackTrace();
            return new java.util.ArrayList<>();
        }
    }

    @Override
    public UserDTO findByEmail(String email) {
        try {
            return userRepository.findByEmail(email);
        } catch (Exception e) {
            System.err.println("이메일로 사용자 조회 중 오류: " + e.getMessage());
            return null;
        }
    }
    
    @Override
    public Messenger saveWithStatus(UserDTO userDTO) {
        return userRepository.saveWithStatus(userDTO);
    }
    
    @Override
    public Messenger deleteWithStatus(UserDTO userDTO) {
        return userRepository.deleteWithStatus(userDTO.getUserId());
    }
    
    @Override
    public Messenger updateWithStatus(UserDTO userDTO) {
        return userRepository.updateWithStatus(userDTO);
    }
    
    @Override
    public Messenger getAllUsersWithStatus() {
        return userRepository.findAllWithStatus();
    }
    
    @Override
    public Messenger checkUserDataStatus() {
        return userRepository.checkDataStatus();
    }

}
