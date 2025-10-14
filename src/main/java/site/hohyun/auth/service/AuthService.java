package site.hohyun.auth.service;

import site.hohyun.auth.domain.LoginRequest;
import site.hohyun.auth.domain.User;

/**
 * 인증 서비스 인터페이스
 * 인증 관련 비즈니스 로직을 정의
 */
public interface AuthService {
    
    /**
     * 사용자 로그인 처리
     * @param loginRequest 로그인 요청 정보
     * @return 로그인 성공 시 사용자 정보, 실패 시 null
     */
    User login(LoginRequest loginRequest);
    
    /**
     * 사용자 회원가입 처리
     * @param user 회원가입할 사용자 정보
     * @return 회원가입 성공 시 사용자 정보, 실패 시 null
     */
    User register(User user);
    
    /**
     * 이메일 중복 체크
     * @param email 체크할 이메일
     * @return 중복 시 true, 사용 가능 시 false
     */
    boolean isEmailExists(String email);
}
