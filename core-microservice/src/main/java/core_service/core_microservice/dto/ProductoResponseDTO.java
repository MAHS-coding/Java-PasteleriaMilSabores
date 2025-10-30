package core_service.core_microservice.dto;

import lombok.Data;

@Data
public class ProductoResponseDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private int stock;
    private boolean disponible;
    private Long categoriaId;
}
