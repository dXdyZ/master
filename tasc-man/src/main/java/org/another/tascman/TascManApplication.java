package org.another.tascman;

import org.another.tascman.model.AnderTask;
import org.another.tascman.model.TaskName;
import org.another.tascman.prog.ServerManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

@SpringBootApplication
public class TascManApplication {

    public static void main(String[] args) {
        SpringApplication.run(TascManApplication.class, args);

        ServerManager serverManager = new ServerManager();
        serverManager.start();
    }
}
