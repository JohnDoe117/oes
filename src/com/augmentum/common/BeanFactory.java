package com.augmentum.common;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class BeanFactory {
    private static Map<String, BeanConfig> beans = new HashMap<String, BeanConfig>();
    private static Map<String, Object> objests = new HashMap<String, Object>();
    private static BeanFactory beanFactory;

    private BeanFactory() {
    }

    public static BeanFactory getInstance() {
        if (beanFactory == null) {
            beanFactory = new BeanFactory();
            beanFactory.init();
        }
        return beanFactory;
    }

    public Object getBean(String id) {
        if (beans.containsKey(id)) {
            BeanConfig bean = beans.get(id);
            String scope = bean.getScope();
            if (scope == null || "".equals(scope)) {
                scope = "singleton";
            }
            if ("singleton".equals(scope)) {
                if (objests.containsKey(id)) {
                    return objests.get(id);
                }
            }
            String className = bean.getClassName();
            Class<?> clz = null;
            try {
                clz = Class.forName(className);
                Object object = clz.newInstance();
                if ("singleton".equals(scope)) {
                    objests.put(id, object);
                }
                List<BeanProperty> beanProperties = bean.getProperties();

                if (beanProperties != null && !beanProperties.isEmpty()) {
                    for (BeanProperty beanProperty : beanProperties) {
                        String propretyName = beanProperty.getName();
                        String firstChar = propretyName.substring(0, 1);
                        String leaveChar = propretyName.substring(1);
                        String methodName = firstChar.toUpperCase() + leaveChar;

                        Method method = null;
                        Method[] methods = clz.getMethods();

                        for (Method methodInClass : methods) {
                            String methodNameClas = methodInClass.getName();
                            if (methodNameClas.equals("set" + methodName)) {
                                method = methodInClass;
                                break;
                            }
                        }

                        String ref = beanProperty.getRef();
                        String value = beanProperty.getValue();
                        if (ref != null && !ref.trim().equals("")) {
                            Object refObject = this.getBean(ref);
                            method.invoke(object, refObject);
                        } else if (value != null && !value.trim().equals("")) {
                            Class<?>[] parmts = method.getParameterTypes();
                            String propertyValue = beanProperty.getValue();
                            if (parmts[0] == String.class) {
                                method.invoke(object, propertyValue);
                            }
                            if (parmts[0] == int.class) {
                                method.invoke(object, Integer.parseInt(propertyValue));
                            }
                            if (parmts[0] == boolean.class) {
                                method.invoke(object, Boolean.parseBoolean(propertyValue));
                            }
                        }
                    }
                }
                if (object.getClass().getPackage().getName().equals("com.augmentum.oes.service.impl")) {
                    ConnectionDynamicProxy connectionDynamicProxy = new ConnectionDynamicProxy();
                    connectionDynamicProxy.setTarget(object);

                    Object proxyObject = Proxy.newProxyInstance(object.getClass().getClassLoader(),
                            object.getClass().getInterfaces(), connectionDynamicProxy);
                    return proxyObject;
                }
                return object;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    private void init() {
        InputStream inputStream = null;
        try {
            inputStream = BeanFactory.class.getClassLoader().getResourceAsStream("bean.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);
            Element element = document.getDocumentElement();

            NodeList beanNodes = element.getElementsByTagName("bean");
            if (beanNodes == null) {
                return;
            }
            int beanLength = beanNodes.getLength();
            for (int i = 0; i < beanLength; i++) {
                Element beanElement = (Element) beanNodes.item(i);
                BeanConfig bean = new BeanConfig();
                String id = beanElement.getAttribute("id");
                bean.setId(id);
                String className = beanElement.getAttribute("class");
                bean.setClassName(className);
                String scope = beanElement.getAttribute("scope");
                bean.setScope(scope);
                beans.put(id, bean);

                NodeList beanPropertyNodes = beanElement.getElementsByTagName("property");

                if (beanPropertyNodes != null) {
                    int beanPropertyLength = beanPropertyNodes.getLength();
                    for (int j = 0; j < beanPropertyLength; j++) {
                        Element beanPropertyElement = (Element) beanPropertyNodes.item(j);
                        BeanProperty beanProperty = new BeanProperty();
                        beanProperty.setName(beanPropertyElement.getAttribute("name"));
                        beanProperty.setRef(beanPropertyElement.getAttribute("ref"));
                        beanProperty.setValue(beanPropertyElement.getAttribute("value"));

                        bean.addProperty(beanProperty);
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
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }
}
