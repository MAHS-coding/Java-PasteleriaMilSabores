package usuario_service.usuario.service.Impl;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import usuario_service.usuario.entity.RefreshTokenEntity;
import usuario_service.usuario.entity.UsuarioEntity;
import usuario_service.usuario.repository.RefreshTokenRepository;
import usuario_service.usuario.repository.UsuarioRepository;
import usuario_service.usuario.service.RefreshTokenService;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public RefreshTokenEntity createRefreshToken(Long usuarioId, String token, long daysToExpire) {
        RefreshTokenEntity entity = new RefreshTokenEntity();
        entity.setToken(token);
        entity.setExpiryDate(Instant.now().plusSeconds(daysToExpire * 24 * 60 * 60));
        UsuarioEntity u = usuarioRepository.findById(usuarioId).orElseThrow();
        entity.setUsuario(u);
        return refreshTokenRepository.save(entity);
    }

    @Override
    public boolean validateRefreshToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .map(t -> t.getExpiryDate().isAfter(Instant.now()))
                .orElse(false);
    }

    @Override
    public RefreshTokenEntity findByToken(String token) {
        return refreshTokenRepository.findByToken(token).orElse(null);
    }

    @Override
    public void deleteByUsuarioId(Long usuarioId) {
        refreshTokenRepository.deleteByUsuarioId(usuarioId);
    }
}
