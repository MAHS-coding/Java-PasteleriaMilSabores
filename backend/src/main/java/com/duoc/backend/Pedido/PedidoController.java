package com.duoc.backend.Pedido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Pedido")
public class PedidoController {

    @Autowired
    private PedidoRepository careRepository;

    @GetMapping
    public List<Pedido> getAllCares() {
        return (List<Pedido>) careRepository.findAll();
    }

    @GetMapping("/{id}")
    public Pedido getCareById(@PathVariable Long id) {
        return careRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Pedido saveCare(@RequestBody Pedido service) {
        return careRepository.save(service);
    }

    @DeleteMapping("/{id}")
    public void deleteCare(@PathVariable Long id) {
        careRepository.deleteById(id);
    }
}