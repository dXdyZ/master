//package org.another.newtaco;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.hamcrest.Matchers.containsString;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest //Тест для HomeController
//public class HomeControllerTest {
//    @Autowired
//    private MockMvc mockMvc; //Внедрить MockMvc
//
//    @Test
//    public void testHomePage() throws Exception {
//        mockMvc.perform(get("/")) //Выполнит запрос GET/
//                .andExpect(status().isOk()) //Ожидает код ответа HTTP 200
//                .andExpect(view().name("home")) //Ожидает имя представления home
//                .andExpect(content().string(containsString("Welcome to..."))); //Ожидает наличие указанной строки
//    }
//}
