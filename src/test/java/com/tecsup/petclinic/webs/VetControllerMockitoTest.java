package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import com.tecsup.petclinic.services.VetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

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

    // ===== INTEGRANTE A: Ana =====

    @Test
    public void testFindAllVets() throws Exception {
        VetDTO vet1 = new VetDTO(1, "James", "Carter", "", "", true);
        VetDTO vet2 = new VetDTO(2, "Helen", "Leary", "", "", true);
        when(vetService.findAll()).thenReturn(List.of(vet1, vet2));

        mockMvc.perform(get("/vets"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].lastName").value("Carter"))
                .andExpect(jsonPath("$[1].lastName").value("Leary"));
    }

    @Test
    public void testFindVetOK() throws Exception {
        VetDTO vet = new VetDTO(1, "James", "Carter", "", "", true);
        when(vetService.findById(1)).thenReturn(vet);

        mockMvc.perform(get("/vets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Carter"));
    }

    @Test
    public void testFindVetKO() throws Exception {
        when(vetService.findById(666)).thenThrow(new VetNotFoundException("Vet not found...!"));

        mockMvc.perform(get("/vets/666"))
                .andExpect(status().isNotFound());
    }

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