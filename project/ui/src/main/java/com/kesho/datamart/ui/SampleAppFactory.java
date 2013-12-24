//package com.kesho.datamart.ui;
//
//import backup.StudentsController;
//import com.kesho.datamart.ui.repository.StudentsRepository;
//import com.kesho.datamart.ui.repository.StudentsRepositoryImpl;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.ImportResource;
//
//@Configuration
//@ImportResource({"classpath:repository-context.xml"})
//@ComponentScan({"com.kesho.datamart.ui.repository", "com.kesho.datamart.service"})
//public class SampleAppFactory
//{
//    @Bean
//    public StudentsController sampleController()
//    {
//        return new StudentsController();
//    }
//
//    public StudentsRepository studentsRepository () {
//        return new StudentsRepositoryImpl();
//    }
//}