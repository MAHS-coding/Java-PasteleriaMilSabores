package core_service.core_microservice.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import core_service.core_microservice.entity.ProductoEntity;
import core_service.core_microservice.entity.RatingEntity;
import core_service.core_microservice.repository.ProductoRepository;
import core_service.core_microservice.repository.RatingRepository;

@RestController
@RequestMapping("/productos/{productoId}/ratings")
public class RatingController {
    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping
    public ResponseEntity<List<RatingEntity>> listarRatings(@PathVariable Long productoId) {
        List<RatingEntity> ratings = ratingRepository.findByProductoId(productoId);
        return ResponseEntity.ok(ratings);
    }

    @PostMapping
    public ResponseEntity<RatingEntity> agregarRating(@PathVariable Long productoId, @RequestBody RatingEntity payload) {
        ProductoEntity producto = productoRepository.findById(productoId).orElseThrow();
        RatingEntity rating = new RatingEntity();
        rating.setProducto(producto);
        rating.setUserEmail(payload.getUserEmail());
        rating.setUserName(payload.getUserName());
        rating.setStars(payload.getStars());
        rating.setComment(payload.getComment());
        rating.setFecha(LocalDateTime.now());
        RatingEntity saved = ratingRepository.save(rating);
        return ResponseEntity.ok(saved);
    }
}
