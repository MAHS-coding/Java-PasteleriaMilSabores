package core_service.core_microservice.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import core_service.core_microservice.dto.CategoriaDTO;
import core_service.core_microservice.entity.CategoriaEntity;
import core_service.core_microservice.repository.CategoriaRepository;
import core_service.core_microservice.service.CategoriaService;

@Service
public class CategoriaServiceImpl implements CategoriaService{
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Override
    public CategoriaDTO mapToDTO(CategoriaEntity categoria) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());
        dto.setDescripcion(categoria.getDescripcion());
        return dto;
    }
    
    @Override
    public CategoriaDTO crearCategoria(CategoriaDTO dto) {
        CategoriaEntity categoria = new CategoriaEntity();
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());

        CategoriaEntity categoriaGuardada = categoriaRepository.save(categoria);
        return mapToDTO(categoriaGuardada);
    }

    @Override
    public List<CategoriaDTO> listarCategorias() {
        List<CategoriaEntity> categorias = categoriaRepository.findAll();
        return categorias.stream().map(this::mapToDTO).toList();
    }

    @Override
    public void eliminarCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }
}
