package com.BankAppliction.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        Filter.super.init(filterConfig);
        LOGGER.info("########## Initiating CustomFilter filter ##########");

    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest requestObject = (HttpServletRequest) request;
        HttpServletResponse responseObject = (HttpServletResponse)  response;

        LOGGER.info("The uri is: {}", requestObject.getRequestURI());

        chain.doFilter(requestObject, responseObject);
        LOGGER.info("Logging Response :{}", responseObject.getContentType());
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
