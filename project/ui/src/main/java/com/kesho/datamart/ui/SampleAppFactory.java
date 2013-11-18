package com.kesho.datamart.ui;

import com.kesho.datamart.ui.controller.StudentsController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({"classpath:repository-context.xml"})
public class SampleAppFactory
{
    @Bean
    public StudentsController sampleController()
    {
        return new StudentsController();
    }
}