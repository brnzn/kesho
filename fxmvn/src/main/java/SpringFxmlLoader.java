import java.io.IOException;
import java.io.InputStream;

import javafx.fxml.FXMLLoader;
import javafx.util.Callback;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringFxmlLoader {

	private ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SampleAppFactory.class);

	public Object load(String url) {
			InputStream fxmlStream = SpringFxmlLoader.class.getResourceAsStream(url);
			System.err.println(SpringFxmlLoader.class.getResourceAsStream(url));
			FXMLLoader loader = new FXMLLoader();
            Object instance = applicationContext.getBean(SampleController.class);
//            loader.getNamespace().put("controller", instance);
            loader.setController(instance);
//			loader.setControllerFactory(new Callback<Class<?>, Object>() {
//
//				@Override
//				public Object call(Class<?> arg0) {
//					return applicationContext.getBean(arg0);
//				}
//				
//			});
			try {
				return loader.load(fxmlStream);
			} catch (IOException e) {
				throw new RuntimeException(e); 
			}
	}
}