package core_service.core_microservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import core_service.core_microservice.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

}
