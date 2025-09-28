package com.duoc.backend.Cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository patientRepository;

    public Iterable<Cliente> getAllPatients() {
        return patientRepository.findAll();
    }

    public Cliente getPatientById(Long id) {
        return patientRepository.findById(id).orElse(null);
    }

    public Cliente savePatient(Cliente patient) {
        return patientRepository.save(patient);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }
}