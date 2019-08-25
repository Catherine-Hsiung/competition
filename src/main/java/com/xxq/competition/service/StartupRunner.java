package com.xxq.competition.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

public class StartupRunner implements CommandLineRunner {
    @Autowired
    WebSocketServer socketServer;
    @Override
    public void run(String... args) throws Exception {
        socketServer.addAllCompetorToSet();
    }
}
