package usuario_service.usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import usuario_service.usuario.entity.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long>{

}
