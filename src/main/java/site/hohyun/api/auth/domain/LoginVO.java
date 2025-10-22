package site.hohyun.api.auth.domain;


public class LoginVO 
{
    // DB나중에 추가할것 임시 계정 생성
    public final String email = "A@test.com";
    public final String password = "1234";


    public String getEmail() 
    {
        return email;
    }

    public String getPassword() 
    {
        return password;
    }
}