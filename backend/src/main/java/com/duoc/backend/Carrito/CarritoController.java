package com.duoc.backend.Carrito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoService invoiceService;

    @GetMapping
    public List<Carrito> getAllInvoices() {
        return (List<Carrito>) invoiceService.getAllInvoices();
    }

    @GetMapping("/{id}")
    public Carrito getInvoiceById(@PathVariable Long id) {
        return invoiceService.getInvoiceById(id);
    }

    @PostMapping
    public Carrito saveInvoice(@RequestBody Carrito invoice) {
        return invoiceService.saveInvoice(invoice);
    }

    @DeleteMapping("/{id}")
    public void deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
    }
}
