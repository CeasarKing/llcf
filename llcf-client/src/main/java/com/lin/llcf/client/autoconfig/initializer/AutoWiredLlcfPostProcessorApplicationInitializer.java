package com.lin.llcf.client.autoconfig.initializer;

import com.lin.llcf.client.autoconfig.postprocessor.AutoWiredLlcfValuePostProcessor;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class AutoWiredLlcfPostProcessorApplicationInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        applicationContext.getBeanFactory().addBeanPostProcessor(new AutoWiredLlcfValuePostProcessor());
    }

}
