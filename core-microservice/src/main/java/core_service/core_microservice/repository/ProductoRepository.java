package core_service.core_microservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import core_service.core_microservice.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByCategoriaNombre(String nombreCategoria);
    List<Producto> findByDisponibleTrue();
}
