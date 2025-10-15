package site.hohyun.api.auth.service;

import org.springframework.stereotype.Service;
import site.hohyun.api.auth.domain.LoginDTO;
import site.hohyun.api.auth.domain.LoginVO;

@Service
public class LoginService 
{
    public boolean login(LoginDTO loginDTO)
    {
        System.out.println("로그인 서비스로 들어옴");
        System.out.println("DTO에서 화면에서 서비스롤 전달된 이메일 = " + loginDTO.getEmail());
        System.out.println("DTO에서 화면에서 서비스롤 전달된 비밀번호 = " + loginDTO.getPassword());

        LoginVO loginVO = new LoginVO();

        System.out.println("VO에서 서비스에서 조회한 이메일 = " + loginVO.getEmail());
        System.out.println("VO에서 서비스에서 조회한 비밀번호 = " + loginVO.getPassword());


        return true;
    }
}

