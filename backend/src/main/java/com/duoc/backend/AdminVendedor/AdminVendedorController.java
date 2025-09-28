package com.duoc.backend.AdminVendedor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/AdminVendedor")
public class AdminVendedorController {

    @Autowired
    private AdminVendedorService medicationService;

    @GetMapping
    public List<AdminVendedor> getAllMedications() {
        return medicationService.getAllMedications();
    }

    @GetMapping("/{id}")
    public AdminVendedor getMedicationById(@PathVariable Long id) {
        return medicationService.getMedicationById(id);
    }

    @PostMapping
    public AdminVendedor saveMedication(@RequestBody AdminVendedor medication) {
        return medicationService.saveMedication(medication);
    }

    @DeleteMapping("/{id}")
    public void deleteMedication(@PathVariable Long id) {
        medicationService.deleteMedication(id);
    }
}