package com.augmentum.oes.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.augmentum.common.ActionConfig;
import com.augmentum.common.BeanFactory;
import com.augmentum.common.ModelAndView;
import com.augmentum.common.ResultConfig;
import com.augmentum.common.ViewParameterConfig;
import com.augmentum.oes.util.StringUtil;

public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String suffix = ".action";
    private Map<String, ActionConfig> actionConfigs = new HashMap<String, ActionConfig>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init();
        String suffixFromConf = config.getInitParameter("suffix");
        if (!StringUtil.isEmpty(suffixFromConf)) {
            suffix = suffixFromConf;
        }
        InputStream inputStream = null;
        try {
            inputStream = DispatcherServlet.class.getClassLoader().getResourceAsStream("action.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);
            Element element = document.getDocumentElement();
            NodeList actionNodes = element.getElementsByTagName("action");
            if (actionNodes == null) {
                return;
            }
            int actionLength = actionNodes.getLength();
            for (int i = 0; i < actionLength; i++) {
                Element actionElement = (Element) actionNodes.item(i);
                ActionConfig actionConfig = new ActionConfig();

                String name = actionElement.getAttribute("name");
                actionConfig.setName(name);
                String clsname = actionElement.getAttribute("class");
                actionConfig.setClsName(clsname);
                String methodName = actionElement.getAttribute("method");
                actionConfig.setMethodName(methodName);

                String httpMethod = actionElement.getAttribute("httpMethod");
                if (StringUtil.isEmpty(httpMethod)) {
                    httpMethod = "GET";
                }
                String[] httpMethodArr = httpMethod.split(",");
                actionConfig.setHttpMethod(httpMethodArr);
                for (String httpMethodItem : httpMethodArr) {
                    actionConfigs.put(name + suffix + "#" + httpMethodItem.toUpperCase(), actionConfig);
                }

                NodeList resustNodes = actionElement.getElementsByTagName("resule");
                if (resustNodes != null) {
                    int resultLength = resustNodes.getLength();
                    for (int j = 0; j < resultLength; j++) {
                        Element resultElement = (Element) resustNodes.item(j);
                        ResultConfig resultConfig = new ResultConfig();

                        String resultName = resultElement.getAttribute("name");
                        resultConfig.setName(resultName);

                        String resultView = resultElement.getAttribute("view");
                        resultConfig.setView(resultView);

                        String resultRedirect = resultElement.getAttribute("redirect");
                        if (StringUtil.isEmpty(resultRedirect)) {
                            resultRedirect = "false";
                        }
                        resultConfig.setRedirect(Boolean.parseBoolean(resultRedirect));

                        String viewParameter = resultElement.getAttribute("viewParameter");

                        if (!StringUtil.isEmpty(viewParameter)) {
                            String[] viewParameterArr = viewParameter.split(",");
                            for (String viewParameterItem : viewParameterArr) {
                                String[] viewParameterItemArr = viewParameterItem.split(":");
                                String key = viewParameterItemArr[0].trim();
                                String from = "attribute";
                                if (viewParameterItemArr.length == 2) {
                                    from = viewParameterItemArr[1].trim();
                                }
                                ViewParameterConfig viewParameterConfig = new ViewParameterConfig(key, from);
                                resultConfig.addViewParameterConfigs(viewParameterConfig);
                            }
                        }
                        actionConfig.addResults(resultName, resultConfig);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public DispatcherServlet() {
        super();
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException,
            IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();
        String requestedUri = uri.substring(request.getContextPath().length() + 1);
        if (requestedUri == null || requestedUri.equals("")) {
            requestedUri = "login" + this.suffix;
        }
        String httpMethod = request.getMethod();

        ActionConfig actionConfig = actionConfigs.get(requestedUri + "#" + httpMethod.toUpperCase());
        HttpSession session = request.getSession();
        if (actionConfig != null) {
            String clsName = actionConfig.getClsName();
            try {
                Class<?>[] param = new Class[2];
                param[0] = Map.class;
                param[1] = Map.class;

                // Class<?> cls = Class.forName(clsName);
                // Object controller = cls.newInstance();
                Object controller = BeanFactory.getInstance().getBean(clsName);
                String methodName = actionConfig.getMethodName();
                java.lang.reflect.Method method = controller.getClass().getMethod(methodName, param);

                Map<String, Object> SessionMap = new HashMap<String, Object>();
                Enumeration<String> toSessionKeys = session.getAttributeNames();
                while (toSessionKeys.hasMoreElements()) {
                    String tokey = toSessionKeys.nextElement();
                    SessionMap.put(tokey, session.getAttribute(tokey));
                }

                Map<String, Object> parameterMap = new HashMap<String, Object>();

                Enumeration<String> toRequesKeys = request.getParameterNames();
                while (toRequesKeys.hasMoreElements()) {
                    String tokey = toRequesKeys.nextElement();
                    parameterMap.put(tokey, request.getParameter(tokey));
                }
                Object[] objects = new Object[2];
                objects[0] = parameterMap;
                objects[1] = SessionMap;

                ModelAndView modelAndView = (ModelAndView) method.invoke(controller, objects);

                Map<String, Object> fromControllerSession = modelAndView.getSessions();
                Set<String> keys = fromControllerSession.keySet();
                for (String key : keys) {
                    session.setAttribute(key, fromControllerSession.get(key));
                }
                Map<String, Object> fromControllerRequest = modelAndView.getRequests();
                Set<String> keyRequests = fromControllerRequest.keySet();
                for (String key : keyRequests) {
                    request.setAttribute(key, fromControllerRequest.get(key));
                }

                String view = modelAndView.getView();
                ResultConfig resultConfig = actionConfig.getResult(view);
                if (resultConfig == null) {
                    if (modelAndView.getIsRedirect()) {
                        response.sendRedirect(request.getContextPath() + view);
                    } else {
                        request.getRequestDispatcher(view).forward(request, response);
                    }
                } else {
                    String resultView = request.getContextPath() + "/" + resultConfig.getView();
                    if (resultConfig.getRedirect()) {
                        List<ViewParameterConfig> viewParameterConfigs = resultConfig.getViewParameterConfigs();
                        if (viewParameterConfigs == null || viewParameterConfigs.isEmpty()) {
                            response.sendRedirect(resultView);
                        } else {
                            StringBuilder sb = new StringBuilder();
                            for (ViewParameterConfig viewParameterConfig : viewParameterConfigs) {
                                String name = viewParameterConfig.getName();
                                String from = viewParameterConfig.getFrom();
                                String value = "";
                                if ("attribute".equals(from)) {
                                    value = (String) request.getAttribute(name);
                                } else if ("parameter".equals(from)) {
                                    value = request.getParameter(name);
                                } else if ("session".equals(from)) {
                                    value = (String) request.getSession().getAttribute(name);
                                } else {
                                    value = (String) request.getAttribute(name);
                                }
                                if (!StringUtil.isEmpty(value)) {
                                    sb.append(name + "=" + value + "&");
                                }
                            }

                            if (resultView.indexOf("?") > 0) {
                                resultView = resultView + "&" + sb.toString();
                            } else {
                                resultView = resultView + "?" + sb.toString();
                            }
                            response.sendRedirect(resultView);
                        }
                    } else {
                        request.getRequestDispatcher(resultConfig.getView()).forward(request, response);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(500);
            }
        } else {
            response.sendError(404);
        }
    }
}
