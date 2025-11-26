package usuario_service.usuario.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import usuario_service.usuario.dto.LoginRequest;
import usuario_service.usuario.dto.LoginResponse;
import usuario_service.usuario.entity.RefreshTokenEntity;
import usuario_service.usuario.entity.UsuarioEntity;
import usuario_service.usuario.repository.UsuarioRepository;
import usuario_service.usuario.security.JwtUtil;
import usuario_service.usuario.service.RefreshTokenService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RefreshTokenService refreshTokenService;

    

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
        List<String> roles = principal.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toList());

        UsuarioEntity usuario = usuarioRepository.findByEmail(request.getEmail()).orElseThrow();

        String accessToken = jwtUtil.generateAccessToken(request.getEmail(), roles);
        String refreshToken = jwtUtil.generateRefreshToken(request.getEmail());

        // persist refresh token
        refreshTokenService.createRefreshToken(usuario.getId(), refreshToken, jwtUtil.getRefreshTokenExpiryDays());

        long expiresIn = jwtUtil.getAccessTokenExpirySeconds();
        return ResponseEntity.ok(new LoginResponse(accessToken, refreshToken, expiresIn));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody java.util.Map<String, String> body) {
        String refreshToken = body.get("refreshToken");
        if (refreshToken == null || !refreshTokenService.validateRefreshToken(refreshToken)) {
            return ResponseEntity.status(401).body("Invalid refresh token");
        }
        RefreshTokenEntity rt = refreshTokenService.findByToken(refreshToken);
        String username = rt.getUsuario().getEmail();
        List<String> roles = rt.getUsuario().getRol() == null ? List.of()
                : rt.getUsuario().getRol().getPermisos().stream().map(p -> p.getNombre()).collect(Collectors.toList());
        String newAccess = jwtUtil.generateAccessToken(username, roles);
        long expiresIn = jwtUtil.getAccessTokenExpirySeconds();
        return ResponseEntity.ok(new LoginResponse(newAccess, refreshToken, expiresIn));
    }
}
