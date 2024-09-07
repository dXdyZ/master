//package org.another.newtaco;
//
//import org.another.newtaco.controller.DesignTacoController;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.hamcrest.Matchers.containsString;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//
//
//@WebMvcTest(DesignTacoController.class)
//public class DesignTacoControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void testDesignPage() throws Exception {
//        mockMvc.perform(get("/design"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("design"))
//                .andExpect(content().string(containsString("Design your taco!")))
//                .andExpect(content().string(containsString("Design your wrap:")))
//                .andExpect(content().string(containsString("Flour Tortilla")))
//                .andExpect(content().string(containsString("Corn Tortilla")))
//                .andExpect(content().string(containsString("Pick your protein:")))
//                .andExpect(content().string(containsString("Ground Beef")))
//                .andExpect(content().string(containsString("Carnitas")))
//                .andExpect(content().string(containsString("Choose your cheese:")))
//                .andExpect(content().string(containsString("Cheddar")))
//                .andExpect(content().string(containsString("Monterry Jack")));
//    }
//
//
//}
//
//
//
//
//
//
