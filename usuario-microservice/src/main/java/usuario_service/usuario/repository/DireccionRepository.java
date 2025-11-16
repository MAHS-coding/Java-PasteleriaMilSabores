package usuario_service.usuario.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import usuario_service.usuario.entity.DireccionEntity;

@Repository
public interface DireccionRepository extends JpaRepository<DireccionEntity, String> {
    List<DireccionEntity> findByUsuarioId(Long usuarioId);
}
