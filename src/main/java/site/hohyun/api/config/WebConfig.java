package site.hohyun.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring Web MVC 설정
 * 인터셉터를 등록하여 특정 URL 패턴에 대한 접근 제어
 */
@Configuration
public class WebConfig implements WebMvcConfigurer 
{

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) 
    {
        registry.addInterceptor(new LoginCheckInterceptor())
                .addPathPatterns("/calculator.html", "/blog.html", "/dashboard.html") // 로그인 체크가 필요한 페이지
                .excludePathPatterns("/", "/index.html", "/login.html", "/login", "/logout", 
                                   "/css/**", "/js/**", "/images/**", "/static/**"); // 제외할 페이지
        
        System.out.println("LoginCheckInterceptor 등록 완료");
    }

    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) 
    {
        // 정적 리소스 핸들러 등록
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        
        // HTML 파일에 대한 핸들러 (템플릿이 아닌 정적 HTML)
        registry.addResourceHandler("/*.html")
                .addResourceLocations("classpath:/static/");
        
        System.out.println("정적 리소스 핸들러 등록 완료");
    }
}
