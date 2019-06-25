package com.lhs.musiclab.config;

import com.lhs.musiclab.inteceptor.UserLoginIntecept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    //视图映射
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/musicIndex").setViewName("music_index");
        registry.addViewController("/test2").setViewName("test2");

    }
    //配置拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //TODO:拦截有问题,把请求都拦截了
        registry.addInterceptor(new UserLoginIntecept()).addPathPatterns("/blog/*","/musicIndex");
//                .excludePathPatterns("/index.html","/index","/custom/**","/image/**","/plugins/**");
    }
}
