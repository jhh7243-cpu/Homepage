
package site.hohyun.api.user.usercontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import site.hohyun.api.user.domain.UserDTO;
import site.hohyun.api.user.service.UserService;
import lombok.RequiredArgsConstructor;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 사용자 관련 컨트롤러
 * 사용자 정보 조회, 수정, 회원가입 등 사용자 관련 기능을 처리
 */
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController 
{

    private final UserService userService;
    private final ResourceLoader resourceLoader;

    /**
     * 회원가입 페이지 표시
     */
    @GetMapping("/register")
    public String registerPage(Model model) 
    {
        return "user/register";
    }

    /**
     * 회원가입 페이지 표시 (대체 경로)
     */
    @GetMapping("")
    public String registerPageAlt(Model model) 
    {
        return "user/register";
    }

    /**
     * 회원가입 처리
     */
    @PostMapping("/submit")
    public String registerUser(@RequestParam("userId") String userId,
                              @RequestParam("username") String username,
                              @RequestParam("password") String password,
                              @RequestParam("name") String name,
                              @RequestParam("email") String email,
                              @RequestParam("phone") String phone,
                              Model model) {
        try {
            // UserDTO 생성
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(userId);
            userDTO.setUsername(username);
            userDTO.setPassword(password);
            userDTO.setName(name);
            userDTO.setEmail(email);
            userDTO.setPhone(phone);

            // UserService를 통해 회원가입 처리
            System.out.println("=== 회원가입 요청 처리 시작 ===");
            System.out.println("회원가입 요청 데이터: " + userDTO.toString());
            
            UserDTO savedUser = userService.registerUser(userDTO);
            
            System.out.println("회원가입 완료: " + savedUser.toString());
            System.out.println("=== 회원가입 요청 처리 완료 ===");

            model.addAttribute("message", "회원가입이 성공적으로 완료되었습니다!");
            return "redirect:/auth/login";
            
        } catch (Exception e) {
            System.err.println("회원가입 오류: " + e.getMessage());
            model.addAttribute("error", "회원가입 중 오류가 발생했습니다: " + e.getMessage());
            return "user/register";
        }
    }

    /**
     * CSV 파일에서 5명의 사용자 데이터 로드
     */
    @GetMapping("/csv")
    public String printFirstFivePassengers(Model model) 
    {
        try {
            System.out.println("=== CSV 데이터 로드 시작 ===");
            
            // CSV 파일을 classpath에서 로드
            Resource resource = resourceLoader.getResource("classpath:static/csv/train.csv");
            System.out.println("CSV 파일 경로: " + resource.getURI());
            
            // CSV 파일 읽기
            FileReader reader = new FileReader(resource.getFile());
            CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());

            List<UserDTO> users = new ArrayList<>();
            int count = 0;

            System.out.println("=== CSV 데이터 파싱 시작 ===");
            // 처음 5명의 데이터만 읽기
            for (CSVRecord record : parser) {
                if (count >= 5) {
                    break;
                }

                UserDTO user = new UserDTO();
                // CSV 파일의 실제 컬럼명에 맞게 수정
                user.setUserId(record.get("PassengerId"));
                user.setUsername(record.get("Name")); // Name을 Username으로 사용
                user.setPassword("default123"); // 기본 비밀번호 설정
                user.setName(record.get("Name"));
                user.setGender(record.get("Sex")); // Sex를 Gender로 사용
                user.setAge(record.get("Age"));
                user.setEmail("user" + record.get("PassengerId") + "@titanic.com"); // 기본 이메일 생성
                user.setPhone("010-0000-" + record.get("PassengerId")); // 기본 전화번호 생성
                user.setPassengerId(record.get("PassengerId"));
                user.setSurvived(record.get("Survived"));
                user.setPclass(record.get("Pclass"));
                user.setTicket(record.get("Ticket"));
                user.setFare(record.get("Fare"));
                user.setCabin(record.get("Cabin"));
                user.setEmbarked(record.get("Embarked"));

                users.add(user);
                count++;
                System.out.println("파싱된 승객 " + count + ": " + user.getName());
            }
            parser.close();
            reader.close();

            System.out.println("=== CSV 파싱 완료: " + users.size() + "명의 승객 데이터 ===");

            // UserService를 통해 데이터 전송 및 파이프라인 확인
            System.out.println("=== UserController에서 UserService로 데이터 전송 시작 ===");
            for (UserDTO userDTO : users) {
                System.out.println("Controller에서 Service로 전송할 DTO: " + userDTO.toString());
                UserDTO savedDTO = userService.registerUser(userDTO);
                System.out.println("Controller에서 Service 결과 받음: " + savedDTO.toString());
                System.out.println("---");
            }

            // 전체 파이프라인 완료 후 최종 Repository 상태 확인
            System.out.println("=== Controller에서 최종 Repository 상태 확인 ===");
            System.out.println("5명의 승객 정보가 모두 Controller → Service → Repository 파이프라인을 통해 저장되었습니다.");
            
            // UserRepository의 printPassengerList 메서드 호출하여 터미널에 승객 명단 출력
            System.out.println("=== 터미널에 승객 명단 출력 ===");
            userService.printPassengerList(users);
            
            System.out.println("=== 파이프라인 디버깅 완료 ===");

            model.addAttribute("message", "5명의 타이타닉 승객 데이터가 성공적으로 로드되었습니다! 터미널을 확인하세요.");
            return "user/register";

        } 
        catch (IOException e) 
        {
            System.err.println("CSV 파일 읽기 오류: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "CSV 파일을 읽는 중 오류가 발생했습니다: " + e.getMessage());
            return "user/register";
        }
        catch (Exception e) 
        {
            System.err.println("예상치 못한 오류: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "데이터 로드 중 오류가 발생했습니다: " + e.getMessage());
            return "user/register";
        }
    }
}
