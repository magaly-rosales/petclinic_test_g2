package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import com.tecsup.petclinic.mappers.VetMapper;
import com.tecsup.petclinic.repositories.VetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class VetServiceImpl implements VetService {

    private final VetRepository vetRepository;
    private final VetMapper vetMapper;

    @Override
    public VetDTO create(VetDTO vetDTO) {
        Vet vet = vetMapper.mapToEntity(vetDTO);
        if (vet.getActive() == null) vet.setActive(true);
        return vetMapper.mapToDto(vetRepository.save(vet));
    }

    @Override
    public VetDTO update(VetDTO vetDTO) {
        Vet vet = vetRepository.save(vetMapper.mapToEntity(vetDTO));
        return vetMapper.mapToDto(vet);
    }

    @Override
    public VetDTO findById(Integer id) throws VetNotFoundException {
        Vet vet = vetRepository.findById(id)
                .orElseThrow(() -> new VetNotFoundException("Vet not found...!"));
        return vetMapper.mapToDto(vet);
    }

    @Override
    public VetDTO deactivate(Integer id) throws VetNotFoundException {
        Vet vet = vetRepository.findById(id)
                .orElseThrow(() -> new VetNotFoundException("Vet not found...!"));
        vet.setActive(false);
        return vetMapper.mapToDto(vetRepository.save(vet));
    }

    @Override
    public VetDTO reactivate(Integer id) throws VetNotFoundException {
        Vet vet = vetRepository.findById(id)
                .orElseThrow(() -> new VetNotFoundException("Vet not found...!"));
        vet.setActive(true);
        return vetMapper.mapToDto(vetRepository.save(vet));
    }

    @Override
    public List<VetDTO> findActiveVets() {
        return vetRepository.findByActive(true).stream().map(vetMapper::mapToDto).toList();
    }

    @Override
    public List<VetDTO> findInactiveVets() {
        return vetRepository.findByActive(false).stream().map(vetMapper::mapToDto).toList();
    }
}