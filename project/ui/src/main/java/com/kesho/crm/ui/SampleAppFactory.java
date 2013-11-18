package com.kesho.crm.ui;

import com.kesho.crm.ui.controller.StudentsController;
import com.kesho.datamart.repository.StudentsRepository;
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