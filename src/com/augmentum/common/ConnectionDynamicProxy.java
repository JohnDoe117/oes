package com.augmentum.common;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;

import com.augmentum.oes.AppContext;
import com.augmentum.oes.util.DBUtil;

public class ConnectionDynamicProxy implements InvocationHandler {
    private Object target;

    public void setTarget(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        Connection conn = (Connection) AppContext.getAppContext().getObjiect("APP_REQUEST_THREAD_CONNECTION");
        boolean needMyClos = false;
        if (conn == null) {
            conn = DBUtil.getConnection();
            // DBUtil.setAutoCommit(conn, false);
            AppContext.getAppContext().addObjiect("APP_REQUEST_THREAD_CONNECTION", conn);
            needMyClos = true;
        }
        try {
            result = method.invoke(target, args);
            // DBUtil.commit(conn);
        } catch (Throwable throwable) {
            // DBUtil.rollback(conn);
            throw throwable;
        } finally {
            if (needMyClos) {
                conn = (Connection) AppContext.getAppContext().getObjiect("APP_REQUEST_THREAD_CONNECTION");
                DBUtil.close(null, null, conn);
                AppContext.getAppContext().removeObject("APP_REQUEST_THREAD_CONNECTION");
                conn = null;
            }
        }
        return result;
    }
}
