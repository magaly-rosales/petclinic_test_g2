package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import com.tecsup.petclinic.services.VetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class VetController {

    private final VetService vetService;

    @GetMapping("/vets")
    public ResponseEntity<List<VetDTO>> findAll(@RequestParam(required = false) Boolean active) {
        if (active == null) return ResponseEntity.ok(vetService.findAll());
        if (active) return ResponseEntity.ok(vetService.findActiveVets());
        return ResponseEntity.ok(vetService.findInactiveVets());
    }

    @GetMapping("/vets/{id}")
    public ResponseEntity<VetDTO> findById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(vetService.findById(id));
        } catch (VetNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/vets")
    public ResponseEntity<VetDTO> create(@RequestBody VetDTO vetDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vetService.create(vetDTO));
    }

    @PutMapping("/vets/{id}/deactivate")
    public ResponseEntity<VetDTO> deactivate(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(vetService.deactivate(id));
        } catch (VetNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/vets/{id}/reactivate")
    public ResponseEntity<VetDTO> reactivate(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(vetService.reactivate(id));
        } catch (VetNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/vets/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        try {
            vetService.delete(id);
            return ResponseEntity.ok("Delete ID: " + id);
        } catch (VetNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}