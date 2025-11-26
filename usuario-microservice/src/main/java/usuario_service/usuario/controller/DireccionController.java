package usuario_service.usuario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import usuario_service.usuario.entity.DireccionEntity;
import usuario_service.usuario.entity.UsuarioEntity;
import usuario_service.usuario.repository.DireccionRepository;
import usuario_service.usuario.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuarios/{usuarioId}/direcciones")
public class DireccionController {
    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<List<DireccionEntity>> listar(@PathVariable Long usuarioId) {
        List<DireccionEntity> dirs = direccionRepository.findByUsuarioId(usuarioId);
        return ResponseEntity.ok(dirs);
    }

    @PostMapping
    public ResponseEntity<DireccionEntity> crear(@PathVariable Long usuarioId, @RequestBody DireccionEntity payload) {
        UsuarioEntity usuario = usuarioRepository.findById(usuarioId).orElseThrow();
        payload.setUsuario(usuario);
        DireccionEntity saved = direccionRepository.save(payload);
        return ResponseEntity.ok(saved);
    }
}
