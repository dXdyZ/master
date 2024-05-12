package org.another.tascman;

import org.another.tascman.prog.ServerManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class TascManApplication {

    public static void main(String[] args) {
        SpringApplication.run(TascManApplication.class, args);

        ServerManager serverManager = new ServerManager();
        serverManager.start();
    }
}
