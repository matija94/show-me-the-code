package com.matija.fonsofttehn.SpringDataAccessJdbc;

import com.matija.fonsofttehn.SpringDataAccessJdbc.config.ApplicationConfig;
import com.matija.fonsofttehn.SpringDataAccessJdbc.model.User;
import com.matija.fonsofttehn.SpringDataAccessJdbc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Main {

    @Autowired
    private UserService userService;

    public static void main(String args[]) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        Main m = context.getBean(Main.class);
        m.workWithService();
    }

    public void workWithService() {
        List<User> users = userService.getAll();

        for (User user : users) {
            System.out.println(user);
        }
    }
}
