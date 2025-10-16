package site.hohyun.api.auth.service;

import org.springframework.stereotype.Service;
import site.hohyun.api.auth.domain.LoginDTO;
import site.hohyun.api.auth.domain.LoginVO;
import site.hohyun.api.common.domain.Messenger;

@Service
public class LoginService 
{
    int code = 0;
    String message = "";
    
    public Messenger login(LoginDTO loginDTO) 
    {
        System.out.println("로그인 서비스로 들어옴");
        System.out.println("DTO에서 화면에서 서비스롤 전달된 이메일 = " + loginDTO.getEmail());
        System.out.println("DTO에서 화면에서 서비스롤 전달된 비밀번호 = " + loginDTO.getPassword());

        LoginVO loginVO = new LoginVO();

        System.out.println("VO에서 서비스에서 조회한 이메일 = " + loginVO.getEmail());
        System.out.println("VO에서 서비스에서 조회한 비밀번호 = " + loginVO.getPassword());

        // 로그인 성공 케이스 (이메일과 비밀번호 모두 일치)
        if (loginVO.getEmail().equals(loginDTO.getEmail()) 
                && loginVO.getPassword().equals(loginDTO.getPassword())) 
        {
            code = 0;
            message = "로그인 성공";
        }
        // 이메일은 일치하지만 비밀번호가 일치하지 않는 경우
        else if (loginVO.getEmail().equals(loginDTO.getEmail())) 
        {
            code = 2;
            message = "비밀번호가 일치하지 않습니다.";
        }
        // 이메일이 일치하지 않는 경우
        else 
        {
            System.out.println("이메일이 일치하지 않습니다.");
            code = 1;
            message = "이메일이 일치하지 않습니다.";
        }
        Messenger messenger = new Messenger();
        messenger.setCode(code);
        messenger.setMessage(message);
        return messenger;
    }
}