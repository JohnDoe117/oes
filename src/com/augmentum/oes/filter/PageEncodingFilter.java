package com.augmentum.oes.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class PageEncodingFilter implements Filter {
    private String encoding = "UTF-8";
    protected FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        if (filterConfig.getInitParameter("encoding") != null)
            encoding = filterConfig.getInitParameter("encoding");
    }

    @Override
    public void doFilter(ServletRequest srequset, ServletResponse sresponse,
            FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) srequset;
        request.setCharacterEncoding(encoding);
        filterChain.doFilter(srequset, sresponse);
    }

    @Override
    public void destroy() {
        this.encoding = null;
    }

}
