package com.personal.springbootconfiguration.config;

import com.personal.springbootconfiguration.security.JWTAuthenticationFilter;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Rishi Kumar
 */
@SpringBootApplication
@Configuration
@EntityScan(basePackages = {"com.personal.springbootconfiguration.model"})
@ComponentScan(basePackages = {"com.personal.springbootconfiguration"})
@EnableAutoConfiguration
@EnableConfigurationProperties({FileStorageProperties.class})
public class Config {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Config.class);
        application.addListeners(new ApplicationPidFileWriter());
        application.run(args);

    }

    @Bean
    public SessionFactory sessionFactory(HibernateEntityManagerFactory hemf) {
        return hemf.getSessionFactory();
    }

    @Bean
    public FilterRegistrationBean jwtFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new JWTAuthenticationFilter());
        registrationBean.addUrlPatterns("/api/*");
        return registrationBean;
    }
}
