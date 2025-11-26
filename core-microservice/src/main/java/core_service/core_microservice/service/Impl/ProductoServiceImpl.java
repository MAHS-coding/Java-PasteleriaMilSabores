package core_service.core_microservice.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import core_service.core_microservice.dto.CategoriaDTO;
import core_service.core_microservice.entity.CategoriaEntity;
import core_service.core_microservice.repository.CategoriaRepository;
import core_service.core_microservice.dto.ProductoRequestDTO;
import core_service.core_microservice.dto.ProductoResponseDTO;
import core_service.core_microservice.entity.ProductoEntity;
import core_service.core_microservice.repository.ProductoRepository;
import core_service.core_microservice.service.ProductoService;

@Service
public class ProductoServiceImpl implements ProductoService{
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public ProductoResponseDTO mapToResponseDTO(ProductoEntity producto) {
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setCategoriaId(producto.getCategoria() != null ? producto.getCategoria().getId() : null);
        dto.setCodigo(producto.getCodigo());
        dto.setImagen(producto.getImagen());
        dto.setStockCritico(producto.getStockCritico());
        return dto;
    }

    @Override
    public ProductoResponseDTO crearProducto(ProductoRequestDTO dto) {
        ProductoEntity producto = new ProductoEntity();
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setDisponible(dto.isDisponible());
        producto.setCodigo(dto.getCodigo());
        producto.setImagen(dto.getImagen());
        producto.setStockCritico(dto.getStockCritico());
        if (dto.getCategoriaId() != null) {
            CategoriaEntity cat = categoriaRepository.findById(dto.getCategoriaId()).orElse(null);
            producto.setCategoria(cat);
        }
        ProductoEntity productoGuardado = productoRepository.save(producto);
        return mapToResponseDTO(productoGuardado);
    }

    @Override
    public List<ProductoResponseDTO> listarProductos() {
        List<ProductoEntity> productos = productoRepository.findAll();
        return productos.stream().map(this::mapToResponseDTO).toList();
    }

    @Override
    public ProductoResponseDTO obtenerProductoPorId(Long id) {
        ProductoEntity producto = productoRepository.findById(id).orElseThrow();
        return mapToResponseDTO(producto);
    }

    @Override
    public ProductoResponseDTO actualizarProducto(Long id, ProductoRequestDTO dto) {
        ProductoEntity producto = productoRepository.findById(id).orElseThrow();
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setDisponible(dto.isDisponible());
        producto.setCodigo(dto.getCodigo());
        producto.setImagen(dto.getImagen());
        producto.setStockCritico(dto.getStockCritico());
        if (dto.getCategoriaId() != null) {
            CategoriaEntity cat = categoriaRepository.findById(dto.getCategoriaId()).orElse(null);
            producto.setCategoria(cat);
        }
        ProductoEntity productoActualizado = productoRepository.save(producto);
        return mapToResponseDTO(productoActualizado);
    }

    @Override
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }
}
