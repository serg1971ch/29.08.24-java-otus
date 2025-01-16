package ru.otus.javaprodi.beanlifecycle;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfiguration {

    @Bean(initMethod = "beanInitMethod", destroyMethod = "beanDestroyMethod")
    public ExperimentImpl experiment() {
        return new ExperimentImpl();
    }
}
