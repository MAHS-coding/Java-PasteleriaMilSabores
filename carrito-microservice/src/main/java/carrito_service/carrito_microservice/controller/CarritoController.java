package carrito_service.carrito_microservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import carrito_service.carrito_microservice.dto.CarritoResponseDTO;
import carrito_service.carrito_microservice.dto.ItemCarritoRequestDTO;
import carrito_service.carrito_microservice.service.Impl.CarritoServiceImpl;

@RestController
@RequestMapping("/carrito")
public class CarritoController {
    @Autowired
    private CarritoServiceImpl carritoService;
    
    @GetMapping("/{usuarioId}")
    public ResponseEntity<CarritoResponseDTO> obtenerCarrito(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(carritoService.obtenerOCrearCarrito(usuarioId));
    }
    
    @PostMapping("/{usuarioId}/items")
    public ResponseEntity<CarritoResponseDTO> agregarItem(@PathVariable Long usuarioId, @RequestBody ItemCarritoRequestDTO dto) {
        return ResponseEntity.ok(carritoService.agregarItemAlCarrito(usuarioId, dto));
    }
    
    @PutMapping("/{usuarioId}/items/{itemId}")
    public ResponseEntity<CarritoResponseDTO> eliminarItem(@PathVariable Long usuarioId, @PathVariable Long itemId) {
        return ResponseEntity.ok(carritoService.eliminarItem(usuarioId, itemId));
    }

    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<Void> vaciarCarrito(@PathVariable Long usuarioId) {
        carritoService.vaciarCarrito(usuarioId);
        return ResponseEntity.noContent().build();
    }
}
