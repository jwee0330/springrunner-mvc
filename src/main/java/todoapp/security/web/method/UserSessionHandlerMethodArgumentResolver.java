package todoapp.security.web.method;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import todoapp.security.UserSession;
import todoapp.security.UserSessionRepository;

public class UserSessionHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver{
	private UserSessionRepository sessionRepository; 
	
	public UserSessionHandlerMethodArgumentResolver(UserSessionRepository sessionRepository) {
		this.sessionRepository = sessionRepository;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		// TODO Auto-generated method stub
		return parameter.getParameterType().isAssignableFrom(UserSession.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		// TODO Auto-generated method stub
		return sessionRepository.get();
	}

}
