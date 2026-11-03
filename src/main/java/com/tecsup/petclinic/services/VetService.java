package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.exceptions.VetNotFoundException;

import java.util.List;

public interface VetService {

    VetDTO create(VetDTO vetDTO);

    VetDTO update(VetDTO vetDTO);

    VetDTO findById(Integer id) throws VetNotFoundException;

    // Soft delete: active = false (no borra el registro)
    VetDTO deactivate(Integer id) throws VetNotFoundException;

    // Vuelve a poner active = true
    VetDTO reactivate(Integer id) throws VetNotFoundException;

    List<VetDTO> findActiveVets();

    List<VetDTO> findInactiveVets();

    List<VetDTO> findAll();

    void delete(Integer id) throws VetNotFoundException;
}