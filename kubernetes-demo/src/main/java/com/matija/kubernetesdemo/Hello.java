package com.matija.kubernetesdemo;

import com.matija.kubernetesdemo.loggingtest.LoggerTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/hello")
public class Hello {

    private static final Logger logger
            = LoggerFactory.getLogger(Hello.class);

    @Autowired
    private LoggerTest loggerTest;

    @RequestMapping("/")
    public String hello() {
        return "Hello world";
    }

    @RequestMapping("/hostname")
    public String hostname() {
        try {
            logger.info("Received hostname request");
            loggerTest.info();
            loggerTest.warn();
            return String.format("Hello from %s", InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            return "Couldn't get hostname";
        }
    }


}
