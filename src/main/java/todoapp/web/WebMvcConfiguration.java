package todoapp.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

import todoapp.commons.web.view.CommaSeparatedValuesView;

/**
 * Spring Web MVC 설정
 *
 * @author springrunner.kr@gmail.com
 */
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        // registry.enableContentNegotiation();
        // 위와 같이 직접 설정하면, 스프링부트가 구성한 ContentNegotiatingViewResolver 전략이 무시된다.
    }

    /**
     * 스프링부트가 생성한 ContentNegotiatingViewResolver를 조작할 목적으로 작성된 컴포넌트
     */
    @Configuration
    public static class ContentNegotiationCustomizer { 

    	@Autowired
        public void configurer(ContentNegotiatingViewResolver contentNegotiatingViewResolver) {
            // TODO ContentNegotiatingViewResolver 사용자 정의
        	List<View> defaultViews = new ArrayList<>(contentNegotiatingViewResolver.getDefaultViews());
        	defaultViews.add(new CommaSeparatedValuesView());
        	contentNegotiatingViewResolver.setDefaultViews(defaultViews);
        }

    }

}
