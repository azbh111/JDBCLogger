package test;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * @author: zyp
 * @since: 2021/11/14 16:36
 */
@Component
public class DataSourceBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof DataSource) {
            return new DataSourceWrapper((DataSource)bean);
        }
        return bean;
    }
}
