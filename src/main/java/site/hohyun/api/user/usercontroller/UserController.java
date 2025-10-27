package site.hohyun.api.user.usercontroller;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.RequiredArgsConstructor;
import site.hohyun.api.common.domain.Messenger;
import site.hohyun.api.user.domain.UserDTO;
import site.hohyun.api.user.service.UserService;

/**
 * 사용자 관련 컨트롤러
 * 사용자 정보 조회, 수정, 회원가입 등 사용자 관련 기능을 처리
 */
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController
{
    private final UserService userService;
    
    /**
     * 사용자 목록 페이지 (기본 홈)
     */
    @GetMapping("")
    public String userListHome(Model model) 
    {
        try {
            List<UserDTO> userList = userService.getAllUsers();
            Messenger statusMessage = userService.getAllUsersWithStatus();
            
            model.addAttribute("userList", userList);
            model.addAttribute("statusMessage", statusMessage);
            model.addAttribute("message", statusMessage.getMessage());
        } catch (Exception e) {
            System.err.println("❌ UserController: 사용자 목록 조회 오류: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "사용자 목록 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
        return "user/list";
    }

    /**
     * 타이타닉 승객 데이터 CSV 파일 로드 및 저장 - GET (브라우저 접근용)
     */
    @GetMapping("/titanic")
    public String loadTitanicDataGet(Model model) 
    {
        return loadTitanicData(model);
    }

    /**
     * 타이타닉 승객 데이터 CSV 파일 로드 및 저장 - POST (API용)
     */
    @PostMapping("/titanic")
    public String loadTitanicData(Model model) 
    
    {
        List<UserDTO> passengerList = new ArrayList<>();
        
        try {
            // CSV 파일 경로
            Resource resource = new ClassPathResource("static/csv/train.csv");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            
            // CSV 파서 생성 (헤더 포함)
            CSVParser csvParser = CSVFormat.DEFAULT.withHeader().parse(bufferedReader);
            
            int recordCount = 0;
            int maxPassengers = Integer.MAX_VALUE; // 모든 데이터 로드
            
            // CSV 레코드를 ArrayList에 저장
            for (CSVRecord record : csvParser) {
                if (recordCount >= maxPassengers) 
                {
                    break;
                }
                
                UserDTO passenger = new UserDTO();
                
                // CSV 헤더를 사용하여 데이터 매핑
                passenger.setUserId(record.get("PassengerId"));                    // 승객ID
                passenger.setUsername(record.get("Name"));                        // 이름
                passenger.setPassword(record.get("Sex"));                          // 성별
                passenger.setName(record.get("Age"));                              // 나이
                passenger.setEmail(record.get("Survived"));                        // 생존여부
                passenger.setPhone(record.get("Ticket"));                          // 티켓번호
                passenger.setPclass(record.get("Pclass"));                        // 객실등급
                passenger.setSibSp(record.get("SibSp"));                           // 형제자매/배우자 수
                passenger.setParch(record.get("Parch"));                           // 부모/자녀 수
                passenger.setFare(record.get("Fare"));                             // 요금
                
                // ArrayList에 승객 데이터 추가
                passengerList.add(passenger);
                recordCount++;
            }
            
            // 리소스 정리
            csvParser.close();
            bufferedReader.close();
            
            // Service로 전체 리스트 전송
            userService.saveAll(passengerList);
            
            // 저장된 데이터 조회
            List<UserDTO> allPassengers = userService.getAllUsers();
            Messenger statusMessage = userService.checkUserDataStatus();
            
            model.addAttribute("userList", allPassengers);
            model.addAttribute("statusMessage", statusMessage);
            model.addAttribute("message", "타이타닉 승객 " + recordCount + "명 데이터 전체 로드 완료! " + statusMessage.getMessage());
            
        } catch (IOException e) {
            model.addAttribute("error", "CSV 파일을 읽는 중 오류가 발생했습니다: " + e.getMessage());
        }
        
        return "user/list";
    }

    /**
     * 사용자 저장 (GET - 브라우저 접근용)
     */
    @GetMapping("/save")
    public String saveUserGet(UserDTO userDTO, Model model) 
    {
        return saveUser(userDTO, model);
    }

    /**
     * 사용자 저장 (POST - API용)
     */
    @PostMapping("/save")
    public String saveUser(UserDTO userDTO, Model model) 
    {
        try {
            Messenger statusMessage = userService.saveWithStatus(userDTO);
            List<UserDTO> userList = userService.getAllUsers();
            
            model.addAttribute("userList", userList);
            model.addAttribute("statusMessage", statusMessage);
            model.addAttribute("message", statusMessage.getMessage());
        } catch (Exception e) {
            model.addAttribute("error", "사용자 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
        return "user/list";
    }

    /**
     * 사용자 수정 (GET - 브라우저 접근용)
     */
    @GetMapping("/update")
    public String updateUserGet(UserDTO userDTO, Model model) 
    {
        return updateUser(userDTO, model);
    }

    /**
     * 사용자 수정 (PUT - API용)
     */
    @PutMapping("/update")
    public String updateUser(UserDTO userDTO, Model model) 
    {
        try {
            Messenger statusMessage = userService.updateWithStatus(userDTO);
            List<UserDTO> userList = userService.getAllUsers();
            
            model.addAttribute("userList", userList);
            model.addAttribute("statusMessage", statusMessage);
            model.addAttribute("message", statusMessage.getMessage());
        } catch (Exception e) {
            model.addAttribute("error", "사용자 수정 중 오류가 발생했습니다: " + e.getMessage());
        }
        return "user/list";
    }

    /**
     * 사용자 삭제 (GET - 브라우저 접근용)
     */
    @GetMapping("/delete/{id}")
    public String deleteUserGet(@PathVariable("id") String id, Model model) 
    {
        return deleteUser(id, model);
    }

    /**
     * 사용자 삭제 (DELETE - API용)
     */
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") String id, Model model) 
    {
        try {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(id);
            Messenger statusMessage = userService.deleteWithStatus(userDTO);
            List<UserDTO> userList = userService.getAllUsers();
            
            model.addAttribute("userList", userList);
            model.addAttribute("statusMessage", statusMessage);
            model.addAttribute("message", statusMessage.getMessage());
        } catch (Exception e) {
            model.addAttribute("error", "사용자 삭제 중 오류가 발생했습니다: " + e.getMessage());
        }
        return "user/list";
    }

    /**
     * 사용자 ID로 조회
     */
    @GetMapping("/find/{id}")
    public String findUserById(@PathVariable("id") String id, Model model) 
    {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(id);
        Messenger messenger = userService.findbyId(userDTO);
        model.addAttribute("user", userDTO);
        model.addAttribute("message", messenger.getMessage());
        return "user/detail";
    }

    /**
     * 사용자명으로 조회
     */
    @GetMapping("/find/username/{username}")
    public String findUserByUsername(@PathVariable("username") String username, Model model) 
    {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        Messenger messenger = userService.findbyId(userDTO);
        model.addAttribute("user", userDTO);
        model.addAttribute("message", messenger.getMessage());
        return "user/detail";
    }

    /**
     * 이메일로 조회
     */
    @GetMapping("/find/email/{email}")
    public String findUserByEmail(@PathVariable("email") String email, Model model) 
    {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(email);
        Messenger messenger = userService.findbyId(userDTO);
        model.addAttribute("user", userDTO);
        model.addAttribute("message", messenger.getMessage());
        return "user/detail";
    }

    /**
     * 모든 사용자 조회
     */
    @GetMapping("/all")
    public String findAllUsers(Model model) 
    {
        try {
            List<UserDTO> userList = userService.getAllUsers();
            Messenger statusMessage = userService.getAllUsersWithStatus();
            
            model.addAttribute("userList", userList);
            model.addAttribute("statusMessage", statusMessage);
            model.addAttribute("message", statusMessage.getMessage());
        } catch (Exception e) {
            model.addAttribute("error", "사용자 목록 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
        return "user/list";
    }
    
    /**
     * 사용자 목록 페이지 (기본)
     */
    @GetMapping("/list")
    public String userList(Model model) 
    {
        try {
            List<UserDTO> userList = userService.getAllUsers();
            Messenger statusMessage = userService.getAllUsersWithStatus();
            
            model.addAttribute("userList", userList);
            model.addAttribute("statusMessage", statusMessage);
            model.addAttribute("message", statusMessage.getMessage());
        } catch (Exception e) {
            model.addAttribute("error", "사용자 목록 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
        return "user/list";
    }

    /**
     * 데이터 상태 확인
     */
    @GetMapping("/status")
    public String checkDataStatus(Model model) 
    {
        try {
            Messenger statusMessage = userService.checkUserDataStatus();
            List<UserDTO> userList = userService.getAllUsers();
            
            model.addAttribute("userList", userList);
            model.addAttribute("statusMessage", statusMessage);
            model.addAttribute("message", statusMessage.getMessage());
        } catch (Exception e) {
            model.addAttribute("error", "데이터 상태 확인 중 오류가 발생했습니다: " + e.getMessage());
        }
        return "user/list";
    }

    
            
}
