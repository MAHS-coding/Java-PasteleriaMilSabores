package usuario_service.usuario.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import usuario_service.usuario.dto.LoginRequestDTO;
import usuario_service.usuario.dto.RolDTO;
import usuario_service.usuario.dto.UsuarioRequestDTO;
import usuario_service.usuario.dto.UsuarioResponseDTO;
import usuario_service.usuario.entity.Rol;
import usuario_service.usuario.entity.Usuario;
import usuario_service.usuario.repository.UsuarioRepository;

public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

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
    public boolean Login(LoginRequestDTO dto) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(dto.getEmail());
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            return usuario.getPassword().equals(dto.getPassword());
        }
        return false;
    }
}
