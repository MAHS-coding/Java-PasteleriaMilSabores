package usuario_service.usuario.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import usuario_service.usuario.dto.RolDTO;
import usuario_service.usuario.dto.UsuarioRequestDTO;
import usuario_service.usuario.dto.UsuarioResponseDTO;
import usuario_service.usuario.entity.RolEntity;
import usuario_service.usuario.entity.UsuarioEntity;
import usuario_service.usuario.repository.RolRepository;
import usuario_service.usuario.repository.UsuarioRepository;
import usuario_service.usuario.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    private UsuarioResponseDTO mapToResponseDTO(UsuarioEntity usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());
        RolDTO rolDTO = new RolDTO();
        rolDTO.setId(usuario.getRol().getId());
        dto.setRol(rolDTO);
        return dto;
    }

    @Override
    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO requestDTO) {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNombre(requestDTO.getNombre());
        usuario.setEmail(requestDTO.getEmail());
        usuario.setPassword(requestDTO.getPassword());
        RolEntity rol = new RolEntity();
        rol.setId(requestDTO.getRolId());
        usuario.setRol(rol);

        UsuarioEntity usuarioGuardado = usuarioRepository.save(usuario);
        return mapToResponseDTO(usuarioGuardado);
    }

    @Override
    public List<UsuarioResponseDTO> listarUsuarios() {
        List<UsuarioEntity> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Override
    public UsuarioResponseDTO obtenerUsuarioPorId(Long id) {
        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return mapToResponseDTO(usuario);
    }
    
    @Override
    public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioRequestDTO requestDTO) {
        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        RolEntity rol = rolRepository.findById(requestDTO.getRolId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        usuario.setNombre(requestDTO.getNombre());
        usuario.setEmail(requestDTO.getEmail());
        usuario.setPassword(requestDTO.getPassword());
        usuario.setRol(rol);

        UsuarioEntity usuarioActualizado = usuarioRepository.save(usuario);
        return mapToResponseDTO(usuarioActualizado);
    }

    @Override
    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }
}
