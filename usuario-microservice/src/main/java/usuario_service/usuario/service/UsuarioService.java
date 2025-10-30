package usuario_service.usuario.service;

import java.util.List;

import usuario_service.usuario.dto.UsuarioRequestDTO;
import usuario_service.usuario.dto.UsuarioResponseDTO;

public interface UsuarioService {
    UsuarioResponseDTO crearUsuario(UsuarioRequestDTO requestDTO);
    List<UsuarioResponseDTO> listarUsuarios();
    UsuarioResponseDTO obtenerUsuarioPorId(Long id);
    UsuarioResponseDTO actualizarUsuario(Long id, UsuarioRequestDTO requestDTO);
    void eliminarUsuario(Long id);
}
