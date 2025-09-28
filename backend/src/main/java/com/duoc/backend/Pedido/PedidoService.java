package com.duoc.backend.Pedido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository careRepository;

    // Obtener todos los servicios de cuidado
    public List<Pedido> getAllCares() {
        return (List<Pedido>) careRepository.findAll();
    }

    // Obtener un servicio de cuidado por ID
    public Pedido getCareById(Long id) {
        return careRepository.findById(id).orElse(null);
    }

    // Guardar un servicio de cuidado
    public Pedido saveCare(Pedido care) {
        return careRepository.save(care);
    }

    // Eliminar un servicio de cuidado por ID
    public void deleteCare(Long id) {
        careRepository.deleteById(id);
    }
}