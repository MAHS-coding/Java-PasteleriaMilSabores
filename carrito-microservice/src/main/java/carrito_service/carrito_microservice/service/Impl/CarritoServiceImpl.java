package carrito_service.carrito_microservice.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import carrito_service.carrito_microservice.dto.CarritoResponseDTO;
import carrito_service.carrito_microservice.dto.ItemCarritoRequestDTO;
import carrito_service.carrito_microservice.dto.ItemCarritoResponseDTO;
import carrito_service.carrito_microservice.entity.CarritoEntity;
import carrito_service.carrito_microservice.entity.ItemCarritoEntity;
import carrito_service.carrito_microservice.repository.CarritoRepository;
import carrito_service.carrito_microservice.repository.ItemCarritoRepository;
import carrito_service.carrito_microservice.service.CarritoService;

@Service
public class CarritoServiceImpl implements CarritoService {
    @Autowired
    private CarritoRepository carritoRepository;
    @Autowired
    private ItemCarritoRepository itemCarritoRepository;

    @Override
    public ItemCarritoResponseDTO mapItemToResponseDTO(ItemCarritoEntity item) {
        ItemCarritoResponseDTO dto = new ItemCarritoResponseDTO();
        dto.setId(item.getId());
        dto.setProductoId(item.getProductoId());
        dto.setNombreProducto(item.getNombreProducto());
        dto.setCantidad(item.getCantidad());
        dto.setPrecioUnitario(item.getPrecioUnitario());
        dto.setSubtotal(item.getSubtotal());
        dto.setMensajePers(item.getMensajePers());
        return dto;
    }

    @Override
    public CarritoResponseDTO mapToResponseDTO(CarritoEntity carrito) {
        CarritoResponseDTO dto = new CarritoResponseDTO();
        dto.setId(carrito.getId());
        dto.setUsuarioId(carrito.getUsuarioId());
        dto.setItems(carrito.getItems().stream().map(this::mapItemToResponseDTO).collect(Collectors.toList()));
        return dto;
    }

    @Override
    public CarritoResponseDTO obtenerOCrearCarrito(Long usuarioId) {
        CarritoEntity carrito = carritoRepository.findByUsuarioId(usuarioId)
                .orElseGet(() ->
                carritoRepository.save(new CarritoEntity(null, usuarioId, new Date(), new ArrayList<>())));
        return mapToResponseDTO(carrito);
    }

    @Override
    public CarritoResponseDTO agregarItemAlCarrito(Long usuarioId, ItemCarritoRequestDTO dto) {
        CarritoEntity carrito = carritoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado para el usuarioId: " + usuarioId));

        ItemCarritoEntity nuevoItem = new ItemCarritoEntity();
        nuevoItem.setProductoId(dto.getProductoId());
        nuevoItem.setNombreProducto(dto.getNombreProducto());
        nuevoItem.setCantidad(dto.getCantidad());
        nuevoItem.setPrecioUnitario(dto.getPrecioUnitario());
        nuevoItem.setMensajePers(dto.getMensajePers());
        nuevoItem.setCarrito(carrito);

        carrito.getItems().add(nuevoItem);
        carritoRepository.save(carrito);

        return mapToResponseDTO(carrito);
    }

    @Override
    public CarritoResponseDTO modificarItem(Long usuarioId, ItemCarritoRequestDTO dto) {
        CarritoEntity carrito = carritoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado para el usuarioId: " + usuarioId));

        ItemCarritoEntity item = itemCarritoRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new RuntimeException("Item no encontrado con id: " + dto.getProductoId()));

        if (!item.getCarrito().getId().equals(carrito.getId())) {
            throw new RuntimeException("El item no pertenece al carrito del usuarioId: " + usuarioId);
        }

        item.setCantidad(dto.getCantidad());
        item.setMensajePers(dto.getMensajePers());

        itemCarritoRepository.save(item);
        return mapToResponseDTO(carrito);
    }

    @Override
    public CarritoResponseDTO eliminarItem(Long usuarioId, Long itemId) {
        CarritoEntity carrito = carritoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado para el usuarioId: " + usuarioId));

        carrito.getItems().removeIf(item -> item.getId().equals(itemId));
        carritoRepository.save(carrito);

        return mapToResponseDTO(carrito);
    }

    @Override
    public void vaciarCarrito(Long usuarioId) {
        CarritoEntity carrito = carritoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado para el usuarioId: " + usuarioId));

        carrito.getItems().clear();
        carritoRepository.save(carrito);
    }
}
