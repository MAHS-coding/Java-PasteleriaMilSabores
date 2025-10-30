package core_service.core_microservice.service;

import java.util.List;

import core_service.core_microservice.dto.ProductoRequestDTO;
import core_service.core_microservice.dto.ProductoResponseDTO;
import core_service.core_microservice.entity.Producto;

public interface ProductoService {
    public ProductoResponseDTO crearProducto(ProductoRequestDTO dto);
    public List<ProductoResponseDTO> listarProductos();
    public ProductoResponseDTO obtenerProductoPorId(Long id);
    public ProductoResponseDTO actualizarProducto(Long id, ProductoRequestDTO dto);
    public void eliminarProducto(Long id);
    public ProductoResponseDTO mapToResponseDTO(Producto producto);
}
