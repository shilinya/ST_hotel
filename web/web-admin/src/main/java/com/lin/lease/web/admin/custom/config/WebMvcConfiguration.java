package com.lin.lease.web.admin.custom.config;


import com.lin.lease.web.admin.custom.converter.StringToBaseEnumConverterFactory;
import com.lin.lease.web.admin.custom.intercepter.AuthenticationIntercepter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//将自己的MvcConverter注入Mvc
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Autowired
    StringToBaseEnumConverterFactory stringToBaseEnumConverterFactory;
    @Autowired
    private AuthenticationIntercepter authenticationIntercepter;
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(stringToBaseEnumConverterFactory);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationIntercepter).addPathPatterns("/admin/**").excludePathPatterns("/admin/login/**");
    }
}
