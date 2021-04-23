package com.keita.permis;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
@Log
public class PermisApplication {

    public static void main(String[] args) {
        SpringApplication.run(PermisApplication.class, args);
    }

    @Scheduled(cron = "0 0 6 * * ?")
    public void doSomething(){
        log.info("Processus Task Scheduler...");
    }

}
