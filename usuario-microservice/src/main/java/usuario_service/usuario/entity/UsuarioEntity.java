package usuario_service.usuario.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String password;
    
    @ManyToOne
    @JoinColumn(name = "rol_id")
    private RolEntity rol;

    // Campos adicionales del JSON
    private String run;
    private java.time.LocalDate fechaNacimiento;

    @jakarta.persistence.OneToMany(mappedBy = "usuario", cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
    private java.util.List<DireccionEntity> addresses = new java.util.ArrayList<>();
}
