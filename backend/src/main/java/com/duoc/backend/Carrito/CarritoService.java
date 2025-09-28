package com.duoc.backend.Carrito;

import com.duoc.backend.Pedido.Pedido;
import com.duoc.backend.AdminVendedor.AdminVendedor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import com.duoc.backend.Pedido.PedidoRepository;
import com.duoc.backend.AdminVendedor.AdminVendedorRepository;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository invoiceRepository;

    @Autowired
    private AdminVendedorRepository medicationRepository;

    @Autowired
    private PedidoRepository careRepository;

    public Iterable<Carrito> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Carrito getInvoiceById(Long id) {
        return invoiceRepository.findById(id).orElse(null);
    }

    public Carrito saveInvoice(Carrito invoice) {
        // Validar que los medicamentos existen
        List<AdminVendedor> validMedications = StreamSupport.stream(medicationRepository.findAllById(invoice.getMedications().stream().map(AdminVendedor::getId).collect(Collectors.toList())
                ).spliterator(), false
        ).collect(Collectors.toList());
        if (validMedications.size() != invoice.getMedications().size()) {
            throw new IllegalArgumentException("Algunos medicamentos no existen en la base de datos.");
        }

        // Validar que los servicios existen
        List<Pedido> validCares = StreamSupport.stream(careRepository.findAllById(invoice.getCares().stream().map(Pedido::getId).collect(Collectors.toList())
                ).spliterator(), false
        ).collect(Collectors.toList());
        if (validCares.size() != invoice.getCares().size()) {
            throw new IllegalArgumentException("Algunos servicios no existen en la base de datos.");
        }

        // Calcular el costo total basado en los servicios y medicamentos asociados
        double totalCareCost = validCares.stream()
                .mapToDouble(Pedido::getCost)
                .sum();

        double totalMedicationCost = validMedications.stream()
                .mapToDouble(AdminVendedor::getCost)
                .sum();

        invoice.setTotalCost(totalCareCost + totalMedicationCost);

        // Guardar la factura en el repositorio
        return invoiceRepository.save(invoice);
    }

    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }
}