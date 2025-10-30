package carrito_service.carrito_microservice.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarritoEntity {
    @Id
    @GeneratedValue
    private Long id;
    private Long usuarioId;//Asociado al usuario propietario del carrito
    private Date fechaCreacion = new Date();

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCarritoEntity> items = new ArrayList<>();
    private double total;

    public double calcularTotal() {
        return items.stream()
                .mapToDouble(ItemCarritoEntity::getSubtotal)
                .sum();
    }
}
