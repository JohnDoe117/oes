package com.augmentum.oes.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.augmentum.oes.Oes;
import com.augmentum.oes.model.User;
import com.augmentum.oes.util.StringUtil;

public class SessionFilter implements Filter {
    private String notNeedLoginPages;
    protected FilterConfig filterconfig;

    @Override
    public void destroy() {
        this.notNeedLoginPages = null;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException,
            ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getRequestURI();
        String requestedUri = uri.substring(request.getContextPath().length() + 1);
        String[] pages = notNeedLoginPages.split(",");
        Boolean isAllow = false;
        for (String page : pages) {
            if (page.equals(requestedUri)) {
                isAllow = true;
                break;
            }
        }
        if (isAllow) {
            filterChain.doFilter(request, response);
        } else {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(Oes.USER);
            if (user == null) {
                if (request.getMethod().toLowerCase().equals("get")) {
                    String queryString = request.getQueryString();
                    String go = requestedUri;
                    if (!StringUtil.isEmpty(queryString)) {
                        go = go + "#" + request.getQueryString();
                    }
                    response.sendRedirect(request.getContextPath() + "/login.action?go=" + go);
                } else {
                    response.sendRedirect(request.getContextPath() + "/login.action");
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }

    }

    @Override
    public void init(FilterConfig filterconfig) throws ServletException {
        this.filterconfig = filterconfig;
        if (filterconfig.getInitParameter("notNeedLoginPages") != null) {
            notNeedLoginPages = filterconfig.getInitParameter("notNeedLoginPages");
        }

    }

}
