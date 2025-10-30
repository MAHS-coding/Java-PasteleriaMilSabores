package carrito_service.carrito_microservice.dto;

import java.util.List;

import lombok.Data;

@Data
public class CarritoResponseDTO {
    private Long id;
    private Long usuarioId;
    private List<ItemCarritoResponseDTO> items;
    private Double total;
}
