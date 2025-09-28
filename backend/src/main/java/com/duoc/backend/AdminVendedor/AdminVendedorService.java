package com.duoc.backend.AdminVendedor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminVendedorService {

    @Autowired
    private AdminVendedorRepository medicationRepository;

    public List<AdminVendedor> getAllMedications() {
        return (List<AdminVendedor>) medicationRepository.findAll();
    }

    public AdminVendedor getMedicationById(Long id) {
        return medicationRepository.findById(id).orElse(null);
    }

    public AdminVendedor saveMedication(AdminVendedor medication) {
        return medicationRepository.save(medication);
    }

    public void deleteMedication(Long id) {
        medicationRepository.deleteById(id);
    }
}