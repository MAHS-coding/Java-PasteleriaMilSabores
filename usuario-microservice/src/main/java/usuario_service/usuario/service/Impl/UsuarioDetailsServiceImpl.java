package usuario_service.usuario.service.Impl;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import usuario_service.usuario.entity.UsuarioEntity;
import usuario_service.usuario.repository.UsuarioRepository;

@Service
public class UsuarioDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioEntity usuario = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario not found"));

        Collection<GrantedAuthority> authorities = usuario.getRol() == null ? java.util.List.of()
                : usuario.getRol().getPermisos().stream()
                        .map(p -> new SimpleGrantedAuthority(p.getNombre()))
                        .collect(Collectors.toList());

        // Also add role name
        if (usuario.getRol() != null) {
            authorities = new java.util.ArrayList<>(authorities);
            authorities.add(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().getNombre()));
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword())
                .authorities(authorities)
                .build();
    }
}
