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

        LoginVO loginVO = new LoginVO();

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
            code = 1;
            message = "이메일이 일치하지 않습니다.";
        }

        Messenger messenger = new Messenger();
        messenger.setMessage(message);
        messenger.setCode(code);
        return messenger;
    }
}