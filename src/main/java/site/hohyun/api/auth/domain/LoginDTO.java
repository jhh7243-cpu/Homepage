package site.hohyun.api.auth.domain;

public class LoginDTO 
{
    private String email;
    private String password;

    // Getter
    public String getEmail() 
    {
        return email;
    }

    public String getPassword() 
    {
        return password;
    }

    // Setter
    public void setEmail(String email) 
    {
        this.email = email;
    }

    public void setPassword(String password) 
    {
        this.password = password;
    }
}
