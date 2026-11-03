package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.services.VetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class VetControllerMockitoTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VetService vetService;

    //Equipo

    // ===== INTEGRANTE B: Magaly =====

    @Test
    public void testCreateVet() throws Exception {
        VetDTO vet = new VetDTO(10, "Magaly", "Rosales", "", "", true);
        when(vetService.create(any(VetDTO.class))).thenReturn(vet);

        mockMvc.perform(post("/vets").contentType(MediaType.APPLICATION_JSON).content("{\"firstName\":\"Magaly\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void testDeactivateVet() throws Exception {
        VetDTO vet = new VetDTO(1, "James", "Carter", "", "", false);
        when(vetService.deactivate(1)).thenReturn(vet);

        mockMvc.perform(put("/vets/1/deactivate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active", is(false)));
    }

    @Test
    public void testReactivateVet() throws Exception {
        VetDTO vet = new VetDTO(6, "Lucia", "Torres", "", "", true);
        when(vetService.reactivate(6)).thenReturn(vet);

        mockMvc.perform(put("/vets/6/reactivate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active", is(true)));
    }
}