package com.BankAppliction.config;

import com.BankAppliction.filters.CustomFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.FilterRegistration;

@Configuration
public class AppConfig {

    @Bean
    public FilterRegistrationBean<CustomFilter> filterRegistrationBean(){

        FilterRegistrationBean<CustomFilter> registrationBean = new FilterRegistrationBean<>();
        CustomFilter customFilter = new CustomFilter();

        registrationBean.setFilter(customFilter);
        registrationBean.addUrlPatterns("/balance");
        return registrationBean;
    }
}
