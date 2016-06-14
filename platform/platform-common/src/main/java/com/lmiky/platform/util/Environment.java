package com.lmiky.platform.util;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.lmiky.platform.util.BundleUtils.EnvironmentType;

/**
 * 环境
 * @author lmiky
 * @date 2013-4-22
 */
public class Environment {
	public static final String WEBINF_NAME = "WEB-INF";
    public static final String CLASSPATH_NAME = "classes";
    private static String classPath; // web应用class目录
    private static String webAppPath; // web应用上下文目录
    private static String contextPath;

    private static ServletContext servletContext;
    private static ApplicationContext applicationContext;

    static {
        // 初始化
        String path = Environment.class.getResource("").getPath();
        // 如果是在jar包内
        if (path.startsWith("file:")) {
            path = path.replaceFirst("file:", "");
        }
        // 需要判断，否则如果是Maven项目，在Junit环境下会报错
        int index = path.toUpperCase().lastIndexOf(WEBINF_NAME + "/");
        if (index != -1) {
            webAppPath = path.substring(0, index);
        } else {
            webAppPath = path;
        }
        webAppPath = webAppPath.replaceAll("%20", " ") + WEBINF_NAME + "/";
        index = path.lastIndexOf(CLASSPATH_NAME + "/");
        if (index != -1) {
            classPath = path.substring(0, index);
            classPath = classPath.replaceAll("%20", " ") + CLASSPATH_NAME + "/";
        } else {
            classPath = webAppPath + CLASSPATH_NAME + "/";
        }
    }

    /**
     * 获取WEB-INF路劲
     * @author lmiky
     * @date 2013-4-22
     * @return
     */
    public static String getWebInfPath() {
        return webAppPath;
    }

    /**
     * 获取classpath目录
     * @author lmiky
     * @date 2013-10-16
     * @return
     */
    public static String getClassPath() {
        return classPath;
    }

    /**
     * @return the contextPath
     */
    /**
     * 获取项目根路径
     * @author lmiky
     * @date 2013-10-18
     * @return
     */
    public static String getContextPath() {
        return contextPath;
    }

    /**
     * 获取bean
     * @author lmiky
     * @date 2013-5-19
     * @param beanName
     * @return
     */
    @SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    /**
     * 获取bean
     * @author lmiky
     * @date 2014年12月12日 下午5:39:53
     * @param beanName
     * @param args
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName, Object... args) {
        return (T) applicationContext.getBean(beanName, args);
    }

    /**
     * @param sc the servletContext to set
     */
    public static void setServletContext(ServletContext servletContext) {
        Environment.servletContext = servletContext;
        Environment.applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        Environment.contextPath = servletContext.getRealPath("/");
        if(applicationContext != null) {
	        String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();
			// 设置文件读取工具部署环境
			if (activeProfiles != null && activeProfiles.length > 0) {
				BundleUtils.setEnvironmentType(EnvironmentType.build(activeProfiles[0]));
			}
        }
    }

    /**
     * @return the servletContext
     */
    public static ServletContext getServletContext() {
        return servletContext;
    }

    /**
     * @return the applicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

	/**
	 * @param applicationContext the applicationContext to set
	 */
	public static void setApplicationContext(ApplicationContext applicationContext) {
		Environment.applicationContext = applicationContext;
	}
}