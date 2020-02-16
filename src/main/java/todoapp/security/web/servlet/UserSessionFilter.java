package todoapp.security.web.servlet;

import java.io.IOException;
import java.security.Principal;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import todoapp.security.UserSession;
import todoapp.security.UserSessionRepository;

/**
 * HttpServletRequest가 로그인 사용자 세션({@link UserSession}을 사용 할 수 있도록 지원하는 필터 구현체
 *
 * @author springrunner.kr@gmail.com
 */
public class UserSessionFilter extends OncePerRequestFilter {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    private UserSessionRepository sessionRepository;

    public UserSessionFilter(UserSessionRepository sessionRepository) {
		this.sessionRepository = sessionRepository;
	}

	@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    	log.info("process user-session servlet filter.");
    	
    	// 전처리 
    	UserSession userSession = sessionRepository.get();
    	HttpServletRequest requestWrapper = new UserSessionRequestWrapper(request, userSession);
    	
    	filterChain.doFilter(requestWrapper, response);
    	
    	// 후처리 
    }


    /**
     * 로그인 사용자 세션을 기반으로 인증 개체와 역할 확인 기능을 제공
     *
     * @author springrunner.kr@gmail.com
     */
    final class UserSessionRequestWrapper extends HttpServletRequestWrapper {

        final Optional<UserSession> userSession;

        protected UserSessionRequestWrapper(HttpServletRequest request, UserSession userSession) {
            super(request);
            this.userSession = Optional.ofNullable(userSession);
        }

        @Override
        public Principal getUserPrincipal() {
        	return userSession.orElse(null);
        }

        @Override
        public boolean isUserInRole(String role) {
        	return userSession.map(it -> it.hasRole(role)).orElse(false);
        }

    }

}
