package ua.kiev.prog;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Created by user on 15.09.2015.
 */
public class WebInit extends WebApplicationInitializer {
    public class WebInitializer implements WebApplicationInitializer{

        @Override
        public void onStartup(ServletContext scx) throws ServletException {
            AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
            ctx.register(WebInit.class);

            ctx.setServletContext(scx);

            ServletRegistration.Dynamic servlet = scx.addServlet("dispatcher", new DispatcherServlet(ctx));
            servlet.addMapping("/");
            servlet.setLoadOnStartup(1);
        }
    }
}
