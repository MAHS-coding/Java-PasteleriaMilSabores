package usuario_service.usuario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import usuario_service.usuario.dto.RolDTO;
import usuario_service.usuario.dto.UsuarioRequestDTO;
import usuario_service.usuario.dto.UsuarioResponseDTO;
import usuario_service.usuario.entity.Rol;
import usuario_service.usuario.entity.Usuario;
import usuario_service.usuario.repository.RolRepository;
import usuario_service.usuario.repository.UsuarioRepository;

public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    private UsuarioResponseDTO mapToResponseDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());
        RolDTO rolDTO = new RolDTO();
        rolDTO.setId(usuario.getRol().getId());
        dto.setRol(rolDTO);
        return dto;
    }

    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO requestDTO) {
        Usuario usuario = new Usuario();
        usuario.setNombre(requestDTO.getNombre());
        usuario.setEmail(requestDTO.getEmail());
        usuario.setPassword(requestDTO.getPassword());
        Rol rol = new Rol();
        rol.setId(requestDTO.getRolId());
        usuario.setRol(rol);

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return mapToResponseDTO(usuarioGuardado);
    }

    public List<UsuarioResponseDTO> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    public UsuarioResponseDTO obtenerUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return mapToResponseDTO(usuario);
    }
    
    public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioRequestDTO requestDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Rol rol = rolRepository.findById(requestDTO.getRolId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        usuario.setNombre(requestDTO.getNombre());
        usuario.setEmail(requestDTO.getEmail());
        usuario.setPassword(requestDTO.getPassword());
        usuario.setRol(rol);

        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        return mapToResponseDTO(usuarioActualizado);
    }

    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }
}
