package org.chassot.auth.services;

import org.chassot.auth.model.Usuario;
import org.chassot.auth.model.dto.UserInfo;
import org.chassot.auth.repository.UsuarioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepo usuarioRepo;

    @Override
    public UserInfo loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepo.findByEmail(email);
        if (usuario == null){
            throw  new UsernameNotFoundException(email);
        }
        return new UserInfo(usuario.getName(), usuario.getEmail(), usuario.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("READ_AUTHORITY")));
    }
}
