package core_service.core_microservice.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import core_service.core_microservice.dto.CategoriaDTO;
import core_service.core_microservice.dto.ProductoRequestDTO;
import core_service.core_microservice.dto.ProductoResponseDTO;
import core_service.core_microservice.entity.Producto;
import core_service.core_microservice.repository.ProductoRepository;
import core_service.core_microservice.service.ProductoService;

@Service
public class ProductoServiceImpl implements ProductoService{
    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public ProductoResponseDTO mapToResponseDTO(Producto producto) {
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setCategoriaId(producto.getCategoria().getId());
        return dto;
    }

    @Override
    public ProductoResponseDTO crearProducto(ProductoRequestDTO dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        
        CategoriaDTO categoria = new CategoriaDTO();
        categoria.setId(dto.getCategoriaId());
        producto.setCategoria(producto.getCategoria());
        Producto productoGuardado = productoRepository.save(producto);
        return mapToResponseDTO(productoGuardado);
    }

    @Override
    public List<ProductoResponseDTO> listarProductos() {
        List<Producto> productos = productoRepository.findAll();
        return productos.stream().map(this::mapToResponseDTO).toList();
    }

    @Override
    public ProductoResponseDTO obtenerProductoPorId(Long id) {
        Producto producto = productoRepository.findById(id).orElseThrow();
        return mapToResponseDTO(producto);
    }

    @Override
    public ProductoResponseDTO actualizarProducto(Long id, ProductoRequestDTO dto) {
        Producto producto = productoRepository.findById(id).orElseThrow();
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        Producto productoActualizado = productoRepository.save(producto);
        return mapToResponseDTO(productoActualizado);
    }

    @Override
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }
}
