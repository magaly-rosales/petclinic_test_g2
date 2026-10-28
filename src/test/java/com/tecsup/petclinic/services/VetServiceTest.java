package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import com.tecsup.petclinic.repositories.VetRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class VetServiceTest {

    private static final Logger log = LoggerFactory.getLogger(VetServiceTest.class);

    @Autowired
    private VetService vetService;

    // ===== INTEGRANTE A: Ana =====

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

    @Test
    public void testUpdateVet() {

        String FIRST_NAME = "Carlos";
        String LAST_NAME = "Perez";
        String EMAIL = "carlos.perez@petclinic.com";
        String PHONE = "999888777";
        Boolean ACTIVE = true;

        String UP_FIRST_NAME = "Carlos2";
        String UP_LAST_NAME = "Perez2";
        String UP_EMAIL = "carlos2.perez@petclinic.com";
        String UP_PHONE = "999111222";
        Boolean UP_ACTIVE = false;

        VetDTO vetDTO = VetDTO.builder()
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .email(EMAIL)
                .phone(PHONE)
                .active(ACTIVE)
                .build();

        // ------------ Create ---------------
        log.info(">" + vetDTO);
        VetDTO vetDTOCreated = this.vetService.create(vetDTO);
        log.info(">>" + vetDTOCreated);

        // ------------ Update ---------------
        vetDTOCreated.setFirstName(UP_FIRST_NAME);
        vetDTOCreated.setLastName(UP_LAST_NAME);
        vetDTOCreated.setEmail(UP_EMAIL);
        vetDTOCreated.setPhone(UP_PHONE);
        vetDTOCreated.setActive(UP_ACTIVE);

        VetDTO upgradeVetDTO = this.vetService.update(vetDTOCreated);
        log.info(">>>>" + upgradeVetDTO);

        //            EXPECTED        ACTUAL
        assertEquals(UP_FIRST_NAME, upgradeVetDTO.getFirstName());
        assertEquals(UP_LAST_NAME, upgradeVetDTO.getLastName());
        assertEquals(UP_EMAIL, upgradeVetDTO.getEmail());
        assertEquals(UP_PHONE, upgradeVetDTO.getPhone());
        assertEquals(UP_ACTIVE, upgradeVetDTO.getActive());
    }

    // ===== INTEGRANTE B: Magaly =====

    @Test
    public void testDeactivateVet() throws VetNotFoundException {
        // Preparar
        Vet vet = new Vet(1, "James", "Carter", "james@mail.com", "999", true);
        when(vetRepository.findById(1)).thenReturn(Optional.of(vet));
        when(vetRepository.save(vet)).thenReturn(vet);

        // Ejecutar
        VetDTO resultado = vetService.deactivate(1);

        // Verificar
        assertFalse(resultado.getActive());
    }

    @Test
    public void testReactivateVet() throws VetNotFoundException {
        // Preparar
        Vet vet = new Vet(6, "Sharon", "Jenkins", "sharon@mail.com", "999", false);
        when(vetRepository.findById(6)).thenReturn(Optional.of(vet));
        when(vetRepository.save(vet)).thenReturn(vet);

        // Ejecutar
        VetDTO resultado = vetService.reactivate(6);

        // Verificar
        assertTrue(resultado.getActive());
    }

    @Test
    public void testFindActiveVets() {
        // Preparar
        Vet vet1 = new Vet(1, "James", "Carter", "james@mail.com", "999", true);
        Vet vet2 = new Vet(2, "Helen", "Leary", "helen@mail.com", "999", true);
        when(vetRepository.findByActive(true)).thenReturn(List.of(vet1, vet2));

        // Ejecutar
        List<VetDTO> resultado = vetService.findActiveVets();

        // Verificar
        assertEquals(2, resultado.size());
    }

    // ===== INTEGRANTE C: Jeff =====

    @Test
    public void testFindVetById_NotFound() {
        Integer idInexistente = 999;

        when(vetRepository.findById(idInexistente)).thenReturn(Optional.empty());

        assertThrows(VetNotFoundException.class, () -> {
            vetService.findById(idInexistente);
        });
    }

    @Test
    public void testDeactivateVet_NotFound() {
        Integer idInexistente = 999;

        when(vetRepository.findById(idInexistente)).thenReturn(Optional.empty());

        assertThrows(VetNotFoundException.class, () -> {
            vetService.deactivate(idInexistente);
        });
    }

    @Test
    public void testFindInactiveVets() {
        // Preparar: 1 veterinario INACTIVO
        Vet vet = new Vet(6, "Sharon", "Jenkins", "sharon@mail.com", "999", false);
        when(vetRepository.findByActive(false)).thenReturn(List.of(vet));

        // Ejecutar
        List<VetDTO> inactivos = vetService.findInactiveVets();

        // Verificar
        assertEquals(1, inactivos.size());
        assertFalse(inactivos.get(0).getActive());
    }
}