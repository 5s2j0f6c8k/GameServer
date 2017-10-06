package com.qwfine.gameserver;


import com.qwfine.core.GameServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Application {

    public static void main(String[] args){
        ApplicationContext context =
                new AnnotationConfigApplicationContext(Application.class);
        GameServer server = context.getBean(GameServer.class);
        server.Start("com.qwfine.gameserver.controller");
    }
}
