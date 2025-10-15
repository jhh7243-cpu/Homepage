package site.hohyun.api.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 로그인 체크 인터셉터
 * 특정 페이지 접근 시 로그인 여부를 확인하여 미로그인 시 로그인 페이지로 리다이렉트
 */
public class LoginCheckInterceptor implements HandlerInterceptor 
{

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception 
    {
        
        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession(false);
        
        // 세션이 없거나 로그인 정보가 없는 경우
        if (session == null || session.getAttribute("isLoggedIn") == null) 
        {
            System.out.println("미인증 사용자 요청: " + requestURI);
            // 로그인 페이지로 리다이렉트
            response.sendRedirect("/login.html?redirectURL=" + requestURI);
            return false;
        }
        
        // 로그인된 사용자는 통과
        System.out.println("인증된 사용자 요청: " + requestURI + " (사용자: " + session.getAttribute("username") + ")");
        return true;
    }
}
