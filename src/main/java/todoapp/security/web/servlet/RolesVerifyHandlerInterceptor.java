package todoapp.security.web.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import todoapp.security.AccessDeniedException;
import todoapp.security.UnauthorizedAccessException;
import todoapp.security.UserSession;
import todoapp.security.UserSessionRepository;
import todoapp.security.support.RolesAllowedSupport;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Role(역할) 기반으로 사용자가 사용 권한을 확인하는 인터셉터 구현체
 *
 * @author springrunner.kr@gmail.com
 */
public class RolesVerifyHandlerInterceptor implements HandlerInterceptor, RolesAllowedSupport {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    private UserSessionRepository sessionRepository;
    
    public RolesVerifyHandlerInterceptor(UserSessionRepository sessionRepository) {
		this.sessionRepository = sessionRepository;
	}

	@Override
    public final boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	if (handler instanceof HandlerMethod) {
    		RolesAllowed rolesAllowed = ((HandlerMethod) handler)
    				.getMethodAnnotation(RolesAllowed.class);
    		
    		// 웹 요청을 보호할 필요가 있다면... (즉, 애노테이션 있다면...)
    		if (Objects.nonNull(rolesAllowed)) {
    			// 1. 로그인 되어 있는가?
    			UserSession session = sessionRepository.get();
    			if (Objects.isNull(session)) {
    				// 로그인 되어 있지 않은 상태...
    				throw new UnauthorizedAccessException();
    			}
    			
    			// 2. 권한은 충분한가?
    			Set<String> matchedRoles = Stream
    					.of(rolesAllowed.value())
    					.filter(role -> session.hasRole(role))
    					.collect(Collectors.toSet());
    			if (matchedRoles.isEmpty()) {
    				throw new AccessDeniedException();
    			}
    		}
    	}
    	return true;
    
    }
    
    

}
