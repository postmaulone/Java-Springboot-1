package com.techno.fromKt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class PathMatchingInterceptor implements WebMvcConfigurer {
    private AuthInterceptor authInterceptor;

    @Autowired
    public PathMatchingInterceptor(AuthInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .excludePathPatterns(
                        "/v1/api/user/login",
                        "/v1/api/user/validate",
                        "/v1/api/user/create"
                );
    }
}
