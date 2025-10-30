package core_service.core_microservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import core_service.core_microservice.entity.ProductoEntity;

public interface ProductoRepository extends JpaRepository<ProductoEntity, Long> {
    List<ProductoEntity> findByCategoriaNombre(String nombreCategoria);
    List<ProductoEntity> findByDisponibleTrue();
}
