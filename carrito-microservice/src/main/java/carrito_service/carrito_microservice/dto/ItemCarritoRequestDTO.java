package carrito_service.carrito_microservice.dto;

import lombok.Data;

@Data
public class ItemCarritoRequestDTO {
    private Long productoId;
    private String nombreProducto;
    private Double precioUnitario;
    private int cantidad;
    private String mensajePers;//Opcional
}
