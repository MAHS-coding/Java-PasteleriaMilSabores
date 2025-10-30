package core_service.core_microservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import core_service.core_microservice.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
