package com.tecsup.petclinic.webs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
@SpringBootTest
@AutoConfigureMockMvc
public class VetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFindAllVets() throws Exception {
        mockMvc.perform(get("/vets"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].firstName").exists());
    }

    @Test
    public void testFindVetOK() throws Exception {
        mockMvc.perform(get("/vets/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.lastName").value("Carter"));
    }
    

    @Test
    public void testFindActiveVets() throws Exception {
        mockMvc.perform(get("/vets")
                        .param("active", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)));
    }

    @Test
    public void testFindInactiveVets() throws Exception {
        mockMvc.perform(get("/vets")
                        .param("active", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testDeleteVetKO() throws Exception {
        mockMvc.perform(delete("/vets/1000"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testFindVetKO() throws Exception {
        mockMvc.perform(get("/vets/666"))
                .andExpect(status().isNotFound());
    }
}