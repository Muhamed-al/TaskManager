package com.tekup.taskmanager.config;


import com.tekup.taskmanager.services.DocumentServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class BeanConfig{


    @Bean
    public String getAppName() {
        return "Task Manager";
    }

    @Bean
    public DocumentServiceImpl documentService() {
        return new DocumentServiceImpl();
    }

}
