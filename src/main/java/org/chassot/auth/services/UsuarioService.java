package org.chassot.auth.services;

import lombok.extern.slf4j.Slf4j;
import org.chassot.auth.model.Usuario;
import org.chassot.auth.repository.UsuarioRepo;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

@Service
@Slf4j
public class UsuarioService {

    @Autowired
    private UsuarioRepo usuarioRepo;

    @Autowired
    private UserVerificationService userVerificationService;

    public Usuario save(Usuario usuario){
        usuario.setCreated(new DateTime(DateTimeUtils.currentTimeMillis()));
        Usuario newUser = usuarioRepo.save(usuario);
        sendVerificationUser(newUser);

        return newUser;
    }

    private void sendVerificationUser(Usuario newUser){
        try {
            log.info(URLEncoder.encode(userVerificationService.generateToken(newUser.getEmail(), newUser.getName(), newUser.getCreated()),"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.info("Gone Wild", e);
        }
    }

    public boolean activeUser(String token){
        if(!userVerificationService.validateToken(token)){
            throw new RuntimeException("Impossível validar token se fodeu");

        }
        Usuario usuario = usuarioRepo.findByEmail(userVerificationService.getUsernameFromToken(token));
        usuario.setActive(true);
        usuarioRepo.save(usuario);
        return true;
    }
}
