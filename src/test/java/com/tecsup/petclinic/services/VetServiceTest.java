package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import com.tecsup.petclinic.repositories.VetRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class VetServiceTest {

    @Autowired
    private VetService vetService;

    @MockitoBean
    private VetRepository vetRepository;

    //Equipo

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