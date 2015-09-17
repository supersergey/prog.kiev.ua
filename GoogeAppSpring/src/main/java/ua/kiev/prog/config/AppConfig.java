package ua.kiev.prog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import ua.kiev.prog.DAO.StudentsDAO;
import ua.kiev.prog.DAO.StudentsDAOImpl;

/**
 * Created by user on 16.09.2015.
 */
@Configuration
@ComponentScan(basePackages = "ua.kiev.prog")
@EnableWebMvc
public class AppConfig extends WebMvcConfigurerAdapter{
    @Bean
    public StudentsDAO studentsDAO()
    {
        return new StudentsDAOImpl();
    }



        @Bean
    public InternalResourceViewResolver viewResolver()
    {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/pages/");
        viewResolver.setSuffix(".jsp");
        //viewResolver.setViewClass(JstlView.class);
        viewResolver.setOrder(1);

        return viewResolver;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
