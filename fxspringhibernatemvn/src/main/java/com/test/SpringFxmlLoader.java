package com.test;

import java.io.IOException;
import java.io.InputStream;

import javafx.fxml.FXMLLoader;

import org.springframework.context.ApplicationContext;
 
public class SpringFxmlLoader
{
    private ApplicationContext context;
 
    public SpringFxmlLoader(ApplicationContext context)
    {
        this.context = context;
    }
 
    public Object load(String url, Class<?> controllerClass) throws IOException
    {
        InputStream fxmlStream = null;
        try
        {
            fxmlStream = controllerClass.getResourceAsStream(url);
            Object instance = context.getBean(controllerClass);
            FXMLLoader loader = new FXMLLoader();
//            loader.getNamespace().put("controller", instance);
            loader.setController(instance);
            return loader.load(fxmlStream);
        }
        finally
        {
            if (fxmlStream != null)
            {
                fxmlStream.close();
            }
        }
    }
}