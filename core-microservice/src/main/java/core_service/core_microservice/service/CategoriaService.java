package core_service.core_microservice.service;

import java.util.List;

import core_service.core_microservice.dto.CategoriaDTO;
import core_service.core_microservice.entity.CategoriaEntity;

public interface CategoriaService {
    public CategoriaDTO crearCategoria(CategoriaDTO dto);
    public List<CategoriaDTO> listarCategorias();
    public void eliminarCategoria(Long id);
    public CategoriaDTO mapToDTO(CategoriaEntity categoria);
}
