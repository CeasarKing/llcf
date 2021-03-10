package com.lin.llcf.client.autoconfig.postprocessor;

import com.lin.llcf.client.refresh.RefreshValueMapHolder;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutoWiredLlcfValuePostProcessor extends AutowiredAnnotationBeanPostProcessor implements ApplicationContextAware {

    private static final Pattern VALUE_PLACEHOLD_PATTERN = Pattern.compile("\\$\\{(.*)}");

    @SneakyThrows
    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            Value valueAnno = field.getDeclaredAnnotation(Value.class);
            if (valueAnno == null) {
                continue;
            }
            String key = resolveKey(valueAnno.value());
            field.setAccessible(true);
            Object currentValue = field.get(bean);
            RefreshValueMapHolder.register(key, currentValue, bean, bean.getClass(), field);
        }
        return pvs;
    }

    private String resolveKey(String valueKey) {
        Matcher matcher = VALUE_PLACEHOLD_PATTERN.matcher(valueKey);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    @Override
    public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {
        // no op
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}
