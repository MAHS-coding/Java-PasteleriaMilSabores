package usuario_service.usuario.dto;

import java.util.Set;

import lombok.Data;

@Data
public class RolDTO {
    private Long id;
    private String nombre;
    private Set<String> permisos;
}
