package carrito_service.carrito_microservice.service;

import carrito_service.carrito_microservice.dto.CarritoResponseDTO;
import carrito_service.carrito_microservice.dto.ItemCarritoRequestDTO;
import carrito_service.carrito_microservice.dto.ItemCarritoResponseDTO;
import carrito_service.carrito_microservice.entity.CarritoEntity;
import carrito_service.carrito_microservice.entity.ItemCarritoEntity;

public interface CarritoService {
    public CarritoResponseDTO obtenerOCrearCarrito(Long usuarioId);
    public CarritoResponseDTO agregarItemAlCarrito(Long usuarioId, ItemCarritoRequestDTO dto);
    public CarritoResponseDTO modificarItem(Long usuarioId, ItemCarritoRequestDTO dto);
    public CarritoResponseDTO eliminarItem(Long usuarioId, Long itemId);
    public void vaciarCarrito(Long usuarioId);
    public CarritoResponseDTO mapToResponseDTO(CarritoEntity carrito);
    public ItemCarritoResponseDTO mapItemToResponseDTO(ItemCarritoEntity item);

}
