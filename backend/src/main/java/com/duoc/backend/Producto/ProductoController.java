package com.duoc.backend.Producto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<Producto> getAllAppointments() {
        return (List<Producto>) productoService.getAllProductos();
    }

    @GetMapping("/{id}")
    public Producto getAppointmentById(@PathVariable Long id) {
        return productoService.getProductotById(id);
    }

    @PostMapping
    public Producto saveAppointment(@RequestBody Producto producto) {
        return productoService.saveProducto(producto);
    }

    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable Long id) {
        productoService.deleteProducto(id);
    }
}
