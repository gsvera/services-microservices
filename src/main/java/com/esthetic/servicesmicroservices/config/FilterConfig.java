package com.esthetic.servicesmicroservices.config;

import com.esthetic.servicesmicroservices.filter.FilterAuthentication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<FilterAuthentication> filterAuthenticationUser() {
        FilterRegistrationBean<FilterAuthentication> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new FilterAuthentication());

        return registrationBean;
    }
}
