package usuario_service.usuario.dto;

public class UsuarioResponseDTO {
    private Long id;
    private String nombre;
    private String email;
    private RolDTO rol;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public RolDTO getRol() {
        return rol;
    }
    public void setRol(RolDTO rol) {
        this.rol = rol;
    }
}
