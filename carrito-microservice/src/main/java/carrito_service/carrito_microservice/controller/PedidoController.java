package carrito_service.carrito_microservice.controller;

import carrito_service.carrito_microservice.dto.PedidoDto;
import carrito_service.carrito_microservice.service.SqsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carrito")
public class PedidoController {

    private final SqsService sqsService;

    public PedidoController(SqsService sqsService) {
        this.sqsService = sqsService;
    }

    @PostMapping("/comprar")
    public ResponseEntity<String> comprar(@RequestBody PedidoDto pedido) {
        String messageId = sqsService.enviarPedido(pedido);
        String body = String.format("Compra procesada exitosamente. ID Seguimiento: %s", messageId);
        return ResponseEntity.ok(body);
    }
}
