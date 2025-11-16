package core_service.core_microservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import core_service.core_microservice.entity.RatingEntity;

@Repository
public interface RatingRepository extends JpaRepository<RatingEntity, Long> {
    List<RatingEntity> findByProductoId(Long productoId);
}
