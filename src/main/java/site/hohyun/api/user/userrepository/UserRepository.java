package site.hohyun.api.user.userrepository;

import org.springframework.stereotype.Repository;
import site.hohyun.api.user.domain.UserDTO;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepository {
    // 실제 데이터를 저장할 Map (메모리 저장소)
    private final Map<String, UserDTO> userStorage = new ConcurrentHashMap<>();

    /**
     * 승객 명단을 터미널에 출력하는 메서드
     */
    public void printPassengerList(List<UserDTO> passengers) {
        System.out.println("\n" + "=".repeat(100));
        System.out.println("                    🚢 타이타닉 승객 명단 🚢");
        System.out.println("=".repeat(100));
        System.out.println();

        if (passengers == null || passengers.isEmpty()) {
            System.out.println("❌ 출력할 승객 데이터가 없습니다.");
            return;
        }

        // 헤더 출력
        System.out.printf("%-8s %-4s %-4s %-30s %-4s %-6s %-8s %-8s %-15s %-8s %-8s %-8s%n",
                "승객ID", "생존", "등급", "이름", "성별", "나이", "형제자매/배우자", "부모/자녀", "티켓", "요금", "객실", "승선항구");

        System.out.println("-".repeat(100));

        // 데이터 출력
        for (UserDTO passenger : passengers) {
            String name = passenger.getName() != null ? passenger.getName() : "N/A";
            String ticket = passenger.getTicket() != null ? passenger.getTicket() : "N/A";
            String cabin = passenger.getCabin() != null ? passenger.getCabin() : "N/A";

            // 긴 문자열은 줄임 처리
            if (name.length() > 30) {
                name = name.substring(0, 27) + "...";
            }
            if (ticket.length() > 15) {
                ticket = ticket.substring(0, 12) + "...";
            }
            if (cabin.length() > 8) {
                cabin = cabin.substring(0, 5) + "...";
            }

            System.out.printf("%-8s %-4s %-4s %-30s %-4s %-6s %-8s %-8s %-15s %-8s %-8s %-8s%n",
                    passenger.getPassengerId() != null ? passenger.getPassengerId() : "N/A",
                    passenger.getSurvived() != null ? passenger.getSurvived() : "N/A",
                    passenger.getPclass() != null ? passenger.getPclass() : "N/A",
                    name,
                    passenger.getGender() != null ? passenger.getGender() : "N/A",
                    passenger.getAge() != null ? passenger.getAge() : "N/A",
                    "0", // SibSp 대체
                    "0", // Parch 대체
                    ticket,
                    passenger.getFare() != null ? passenger.getFare() : "N/A",
                    cabin,
                    passenger.getEmbarked() != null ? passenger.getEmbarked() : "N/A");
        }

        System.out.println();
        System.out.println("=".repeat(100));
        System.out.println("✅ 총 " + passengers.size() + "명의 승객 데이터가 터미널에 출력되었습니다!");
        System.out.println("=".repeat(100) + "\n");
    }

    // UserService에서 필요한 메서드들 추가
    public UserDTO save(UserDTO userDTO) {
        System.out.println("UserRepository.save() 호출됨 - 저장할 DTO: " + userDTO.toString());
        
        // UserId를 키로 사용하여 저장
        String key = userDTO.getUserId();
        if (key == null || key.isEmpty()) {
            // UserId가 없으면 Username을 키로 사용
            key = userDTO.getUsername();
        }
        
        userStorage.put(key, userDTO);
        System.out.println("UserRepository에 데이터 저장 완료! 키: " + key + ", 총 저장된 사용자 수: " + userStorage.size());
        
        return userDTO;
    }
    
    public List<UserDTO> findAll() {
        System.out.println("UserRepository.findAll() 호출됨 - 현재 저장된 사용자 수: " + userStorage.size());
        return new ArrayList<>(userStorage.values());
    }
    
    public UserDTO findById(String userId) {
        System.out.println("UserRepository.findById() 호출됨: " + userId);
        UserDTO found = userStorage.get(userId);
        if (found != null) {
            System.out.println("사용자를 찾았습니다: " + found.toString());
        } else {
            System.out.println("사용자를 찾을 수 없습니다: " + userId);
        }
        return found;
    }
    
    public UserDTO findByUsername(String username) {
        System.out.println("UserRepository.findByUsername() 호출됨: " + username);
        for (UserDTO user : userStorage.values()) {
            if (username.equals(user.getUsername())) {
                System.out.println("사용자명으로 사용자를 찾았습니다: " + user.toString());
                return user;
            }
        }
        System.out.println("사용자명으로 사용자를 찾을 수 없습니다: " + username);
        return null;
    }
    
    public UserDTO findByEmail(String email) {
        System.out.println("UserRepository.findByEmail() 호출됨: " + email);
        for (UserDTO user : userStorage.values()) {
            if (email.equals(user.getEmail())) {
                System.out.println("이메일로 사용자를 찾았습니다: " + user.toString());
                return user;
            }
        }
        System.out.println("이메일로 사용자를 찾을 수 없습니다: " + email);
        return null;
    }

    public void printAllDTOs() {
        System.out.println("UserRepository.printAllDTOs() 호출됨");
        List<UserDTO> allUsers = findAll();
        if (allUsers.isEmpty()) {
            System.out.println("저장된 사용자가 없습니다.");
        } else {
            for (UserDTO user : allUsers) {
                System.out.println("사용자: " + user.toString());
            }
        }
    }

    public String getDTOCount() {
        System.out.println("UserRepository.getDTOCount() 호출됨");
        List<UserDTO> allUsers = findAll();
        return "총 사용자 수: " + allUsers.size();
    }
}