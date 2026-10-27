package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class VetServiceTest {

    private static final Logger log = LoggerFactory.getLogger(VetServiceTest.class);

    @Autowired
    private VetService vetService;

    @Test
    public void testFindVetById() {

        final String FIRST_NAME_EXPECTED = "James";
        final String LAST_NAME_EXPECTED = "Carter";

        Integer ID = 1;

        VetDTO vet = null;

        try {
            vet = this.vetService.findById(ID);
        } catch (VetNotFoundException e) {
            fail(e.getMessage());
        }
        log.info("Veterinario encontrado: " + vet);
        assertEquals(FIRST_NAME_EXPECTED, vet.getFirstName());
        assertEquals(LAST_NAME_EXPECTED, vet.getLastName());
    }
    @Test
    public void testCreateVet() {

        String FIRST_NAME = "Ana";
        String LAST_NAME = "Merino";
        String EMAIL = "ana.merino@petclinic.com";
        String PHONE = "987654321";
        Boolean ACTIVE = true;

        VetDTO vetDTO = VetDTO.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .phone(PHONE)
                .active(ACTIVE)
                .build();

        VetDTO newVetDTO = this.vetService.create(vetDTO);

        log.info("VET CREATED: " + newVetDTO.toString());

        assertNotNull(newVetDTO.getId());
        assertEquals(FIRST_NAME, newVetDTO.getFirstName());
        assertEquals(LAST_NAME, newVetDTO.getLastName());
        assertEquals(EMAIL, newVetDTO.getEmail());
        assertEquals(PHONE, newVetDTO.getPhone());
        assertEquals(ACTIVE, newVetDTO.getActive());
    }
}