package carrito_service.carrito_microservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import carrito_service.carrito_microservice.entity.ItemCarritoEntity;

public interface ItemCarritoRepository extends JpaRepository<ItemCarritoEntity, Long> {

}
