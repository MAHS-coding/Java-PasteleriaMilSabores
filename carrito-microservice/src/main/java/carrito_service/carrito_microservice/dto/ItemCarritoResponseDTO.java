package carrito_service.carrito_microservice.dto;

import lombok.Data;

@Data
public class ItemCarritoResponseDTO {
    private Long id;
    private Long productoId;
    private String nombreProducto;
    private Double precioUnitario;
    private int cantidad;
    private Double subtotal;
    private String mensajePers;//Opcional
}
