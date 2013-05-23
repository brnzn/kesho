package com.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SampleAppFactory
{
    @Bean
    public OldPerson person()
    {
        return new OldPerson("Richard");
    }
 
    @Bean
    public SampleController sampleController()
    {
        return new SampleController();
    }
}