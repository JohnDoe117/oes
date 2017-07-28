package com.augmentum.oes.filter;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.augmentum.oes.AppContext;
import com.augmentum.oes.Oes;
import com.augmentum.oes.util.DBUtil;

public class AppContextFilter implements Filter {

    public AppContextFilter() {

    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException,
            ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        AppContext appContext = AppContext.getAppContext();
        appContext.addObjiect(Oes.APP_CONTEXT_REQUEST, request);
        appContext.addObjiect(Oes.APP_CONTEXT_RESPONSE, response);
        Connection conn = (Connection) AppContext.getAppContext().getObjiect("APP_REQUEST_THREAD_CONNECTION");
        boolean needMyClos = false;
        if (conn == null) {
            conn = DBUtil.getConnection();
            AppContext.getAppContext().addObjiect("APP_REQUEST_THREAD_CONNECTION", conn);
            needMyClos = true;
        }
        try {
            filterChain.doFilter(request, response);
        } catch (IOException ioException) {
            throw ioException;
        } catch (ServletException servletException) {
            throw servletException;
        } catch (RuntimeException runtimeException) {
            throw runtimeException;
        } finally {
            appContext.clear();
            if (needMyClos) {
                conn = (Connection) AppContext.getAppContext().getObjiect("APP_REQUEST_THREAD_CONNECTION");
                DBUtil.close(null, null, conn);
            }
        }

    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException {

    }

}
