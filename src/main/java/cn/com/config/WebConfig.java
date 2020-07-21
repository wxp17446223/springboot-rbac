package cn.com.config;

import cn.com.interceptor.SecurityInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Resource
    private SecurityInterceptor securityInterceptor;


    /**
     * 注册拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptor= registry.addInterceptor(securityInterceptor);
        interceptor.addPathPatterns("/**");
        interceptor.excludePathPatterns("/login","/error","/logout","/layui/**");
        interceptor.excludePathPatterns("/**/*.js");
        interceptor.excludePathPatterns("/**/*.css");
        interceptor.excludePathPatterns("/**/*.jpg");
        interceptor.excludePathPatterns("/**/*.png");
        interceptor.excludePathPatterns("/**/*.bmp");
        interceptor.excludePathPatterns("/**/*.gif");
        interceptor.excludePathPatterns("/**/*.ttf");
        interceptor.excludePathPatterns("/**/*.eot");
        interceptor.excludePathPatterns("/**/*.svg");
        interceptor.excludePathPatterns("/**/*.woff");
        interceptor.excludePathPatterns("/**/*.woff2");



    }
}
