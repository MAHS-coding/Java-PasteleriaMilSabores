package core_service.core_microservice.dto;

import lombok.Data;

@Data
public class ProductoRequestDTO {
    private String nombre;
    private String descripcion;
    private Double precio;
    private int stock;
    private boolean disponible;
    private Long categoriaId;
    private String codigo;
    private String imagen;
    private Integer stockCritico;
}
