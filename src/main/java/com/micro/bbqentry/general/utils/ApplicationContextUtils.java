package com.micro.bbqentry.general.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * ApplicationContextUtility (used for get beans of ioc)  singleton
 *
 * @author jockeys
 * @since 2020/2/26
 */
@Component
public final class ApplicationContextUtils implements ApplicationContextAware {
    private ApplicationContext appctx;

    private static ApplicationContextUtils instance = new ApplicationContextUtils();

    private ApplicationContextUtils() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        instance.appctx = applicationContext;
    }

    /**
     * get applicationContext
     *
     * @return applicationContext singleton
     */
    public static ApplicationContext getApplicationContext() {
        return instance.appctx;
    }

    /**
     * get bean
     *
     * @param name bean name
     * @return bean
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * get bean
     *
     * @param clazz bean class
     * @param <T>   bean classname
     * @return bean
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * get bean
     *
     * @param name  bean name
     * @param clazz bean class
     * @param <T>   bean classname
     * @return
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }


}
