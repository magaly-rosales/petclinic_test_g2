package com.tecsup.petclinic.webs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class VetControllerTest {

    @Autowired
    private MockMvc mockMvc;



    // ===== INTEGRANTE B: Magaly =====

    @Test
    public void testCreateVet() throws Exception {
        String json = "{\"firstName\":\"Magaly\",\"lastName\":\"Rosales\",\"email\":\"magaly@mail.com\",\"phone\":\"999\",\"active\":true}";

        mockMvc.perform(post("/vets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is("Magaly")));
    }

    @Test
    public void testDeactivateVet() throws Exception {
        mockMvc.perform(put("/vets/1/deactivate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active", is(false)));
    }

    @Test
    public void testReactivateVet() throws Exception {
        mockMvc.perform(put("/vets/6/reactivate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active", is(true)));
    }
}