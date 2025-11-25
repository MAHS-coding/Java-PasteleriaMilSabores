package usuario_service.usuario.service;

import usuario_service.usuario.entity.RefreshTokenEntity;

public interface RefreshTokenService {
    RefreshTokenEntity createRefreshToken(Long usuarioId, String token, long daysToExpire);
    boolean validateRefreshToken(String token);
    RefreshTokenEntity findByToken(String token);
    void deleteByUsuarioId(Long usuarioId);
}
