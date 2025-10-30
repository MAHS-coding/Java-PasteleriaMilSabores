package core_service.core_microservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import core_service.core_microservice.entity.CategoriaEntity;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long> {

}
