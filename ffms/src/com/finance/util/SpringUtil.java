// 
// 
// 

package com.finance.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringUtil implements ApplicationContextAware
{
    private static ApplicationContext applicationContext;
    
    public void setApplicationContext(final ApplicationContext arg0) throws BeansException {
        SpringUtil.applicationContext = arg0;
    }
    
    public static Object getObject(final String id) {
        Object object = null;
        object = SpringUtil.applicationContext.getBean(id);
        return object;
    }
}
