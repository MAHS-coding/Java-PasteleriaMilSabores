package com.duoc.backend.Carrito;

import com.duoc.backend.Pedido.Pedido;
import com.duoc.backend.AdminVendedor.AdminVendedor;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String patientName;
    private LocalDate date;
    private LocalTime time;

    @ManyToMany
    @JoinTable(
        name = "invoice_cares",
        joinColumns = @JoinColumn(name = "invoice_id"),
        inverseJoinColumns = @JoinColumn(name = "care_id")
    )
    private List<Pedido> cares;


    @ManyToMany
    @JoinTable(
        name = "invoice_medications",
        joinColumns = @JoinColumn(name = "invoice_id"),
        inverseJoinColumns = @JoinColumn(name = "medication_id")
    )
    private List<AdminVendedor> medications;


    private Double totalCost;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public List<Pedido> getCares() {
        return cares;
    }

    public void setCares(List<Pedido> cares) {
        this.cares = cares;
    }

    public List<AdminVendedor> getMedications() {
        return medications;
    }

    public void setMedications(List<AdminVendedor> medications) {
        this.medications = medications;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }
}