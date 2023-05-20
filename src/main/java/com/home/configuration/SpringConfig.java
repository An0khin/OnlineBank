package com.home.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
@SpringBootApplication
@EnableWebMvc
@EnableScheduling
@ComponentScan("com.home")
@Import({SecurityConfig.class})

@EnableJpaRepositories(basePackages = "com.home.dao")
@EntityScan("com.home.model")
@EnableTransactionManagement
public class SpringConfig implements WebMvcConfigurer {
    public static void main(String[] args) {
        SpringApplication.run(SpringConfig.class, args);
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/css/");
    }
}
