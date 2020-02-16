package todoapp.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import todoapp.web.model.SiteProperties;
/**
 * ControllerAdvice 는 restController, controller 모두 다 잡기 때문
 * 뷰만 필요한 커스텀 어노테이션을 생성해서 사용하는 
 * @author jayden
 *
 */
@ControllerAdvice
public class GlobalControllerAdvice {
	
	private SiteProperties siteProperties;

	public GlobalControllerAdvice(SiteProperties siteProperties) {
		this.siteProperties = siteProperties;
	}
	
	@ModelAttribute("site")
	public SiteProperties siProperties() {
		return siteProperties;
	}

}
