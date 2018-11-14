package com.matija.fonsofttehn.SpringDataAccessJdbc.config;

import com.matija.fonsofttehn.SpringDataAccessJdbc.repo.UserRepository;
import com.matija.fonsofttehn.SpringDataAccessJdbc.repo.impl.UserRepositoryImpl;
import com.matija.fonsofttehn.SpringDataAccessJdbc.service.UserService;
import com.matija.fonsofttehn.SpringDataAccessJdbc.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


@Configuration
@ComponentScan(basePackages = {"com.matija.fonsofttehn.SpringDataAccessJdbc"})
public class ApplicationConfig {


   /* @Bean
    UserService getUserService() {
        return new UserServiceImpl();
    }

    @Bean
    UserRepository getUserRepository() {
        return new UserRepositoryImpl();
    }*/

    @Bean
    DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/springdataaccessjdbc");
        dataSource.setUsername("root");
        dataSource.setPassword("sjm2254wow");
        return dataSource;
    }
}
