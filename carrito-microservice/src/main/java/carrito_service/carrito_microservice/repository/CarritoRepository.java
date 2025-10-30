package carrito_service.carrito_microservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import carrito_service.carrito_microservice.entity.CarritoEntity;

@Repository
public interface CarritoRepository extends JpaRepository<CarritoEntity, Long> {
    Optional<CarritoEntity> findByUsuarioId(Long usuarioId);
}
