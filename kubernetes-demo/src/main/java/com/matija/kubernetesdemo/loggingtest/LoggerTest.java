package com.matija.kubernetesdemo.loggingtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoggerTest {

    private static final Logger logger
            = LoggerFactory.getLogger(LoggerTest.class);



    public void info() {
        logger.info("Hello from INFO log");
    }

    public void warn() {
        logger.warn("Hello from WARN log");
    }

}
