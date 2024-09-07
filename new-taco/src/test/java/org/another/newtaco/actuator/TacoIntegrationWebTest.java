//package org.another.newtaco.reactor;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.reactive.server.WebTestClient;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class TacoIntegrationWebTest {
//    @Autowired
//    private WebTestClient testClient;
//
//    @Test
//    public void shouldReturnRecentTacos() {
//        testClient.get().uri("/reactor?recent")
//                .accept(MediaType.APPLICATION_JSON)
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody()
//                    .jsonPath("$").isArray()
//                    .jsonPath("$.length()").isEqualTo(3)
//                    .jsonPath("$[?(@.name == 'helloTestArtemis')]").exists()
//                    .jsonPath("$[?(@.name == 'test2tacos')]").exists()
//                    .jsonPath("$[?(@.name == 'testrabbitmq')]").exists();
//
//    }
//}
//
//
//
//
//
//
