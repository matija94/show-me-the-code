package com.matija.kubernetesdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/liveness")
public class LivenessController {

    private static final Logger logger
            = LoggerFactory.getLogger(LivenessController.class);

    private AtomicInteger counter;

    @PostConstruct
    public void init() {
        counter = new AtomicInteger();
    }

    @RequestMapping("/")
    public ResponseEntity<?> liveness() {
        int currentCount = counter.getAndIncrement();
        logger.info("currentCount={}", currentCount);
        if (currentCount > 3) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
