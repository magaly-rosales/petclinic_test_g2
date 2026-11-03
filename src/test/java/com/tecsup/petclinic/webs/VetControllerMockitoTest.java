package com.tecsup.petclinic.webs;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.services.VetService;
import com.tecsup.petclinic.exceptions.VetNotFoundException;

@SpringBootTest
@AutoConfigureMockMvc
public class VetControllerMockitoTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VetService vetService;

    @Test
    public void testFindAllVets() throws Exception {
        VetDTO vet1 = new VetDTO();
        vet1.setId(1);
        vet1.setFirstName("James");
        vet1.setLastName("Carter");

        VetDTO vet2 = new VetDTO();
        vet2.setId(2);
        vet2.setFirstName("Helen");
        vet2.setLastName("Leary");

        List<VetDTO> vets = Arrays.asList(vet1, vet2);

        given(vetService.findAll()).willReturn(vets);

        mockMvc.perform(get("/vets"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].lastName").value("Carter"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].lastName").value("Leary"));
    }

    @Test
    public void testFindVetOK() throws Exception {
        VetDTO vet = new VetDTO();
        vet.setId(1);
        vet.setFirstName("James");
        vet.setLastName("Carter");

        given(vetService.findById(1)).willReturn(vet);

        mockMvc.perform(get("/vets/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.lastName").value("Carter"));
    }

    @Test
    public void testFindVetKO() throws Exception {
        // Ahora sí usamos la excepción correcta del proyecto
        given(vetService.findById(666)).willThrow(new VetNotFoundException("Vet not found...!"));

        mockMvc.perform(get("/vets/666"))
                .andExpect(status().isNotFound());
    }
}