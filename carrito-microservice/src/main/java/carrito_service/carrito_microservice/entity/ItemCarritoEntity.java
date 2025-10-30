package carrito_service.carrito_microservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "items_carrito")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCarritoEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    //Referencia al microservicio Core
    private Long productoId;
    private String nombreProducto;
    private Double precioUnitario;
    private int cantidad;

    private String mensajePers;//Opcional

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="carrito_id")
    private CarritoEntity carrito;

    public Double getSubtotal() {
        return precioUnitario * cantidad;
    }
}
