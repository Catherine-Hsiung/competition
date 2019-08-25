package com.xxq.competition;

import com.xxq.competition.service.WebSocketServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan("com.xxx.competition.mapper")
public class CompetitionApplication {

    public static void main(String[] args) {
        SpringApplication competitionApplication
                = new SpringApplication(CompetitionApplication.class);
        ConfigurableApplicationContext context = competitionApplication.run(args);
        WebSocketServer.setApplicationContext(context);
    }

}
