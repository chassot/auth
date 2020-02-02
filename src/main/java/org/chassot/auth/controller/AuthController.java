package org.chassot.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.chassot.auth.model.Token;
import org.chassot.auth.model.Usuario;
import org.chassot.auth.model.dto.ClientCredential;
import org.chassot.auth.model.dto.NewUser;
import org.chassot.auth.model.dto.User;
import org.chassot.auth.services.JwtTokenService;
import org.chassot.auth.services.JwtUserDetailsService;
import org.chassot.auth.services.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/auth")
@Slf4j
public class AuthController {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private UsuarioService usuarioService;

    ModelMapper modelMapper = new ModelMapper();

    @PostMapping
    public ResponseEntity<Token> authenticate(@RequestBody ClientCredential clientCredential) throws Exception {
        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(clientCredential.getUid());
        final String token = jwtTokenService.generateToken(userDetails);
        return ResponseEntity.ok(new Token(token));
    }

    @PostMapping(path = "register")
    public ResponseEntity<User> register(@RequestBody NewUser newUser) throws Exception {
        log.debug(newUser.toString());
        Usuario usuario = modelMapper.map(newUser, Usuario.class);

        Usuario usuarioSalvo = usuarioService.save(usuario);

        User retorno = modelMapper.map(usuarioSalvo, User.class);
        retorno.setToken(jwtTokenService.generateToken(
                jwtUserDetailsService.loadUserByUsername(usuarioSalvo.getEmail())));
        return ResponseEntity.ok(retorno);
    }

    @GetMapping(path = "/active/{token}")
    public ResponseEntity.BodyBuilder active(@PathVariable String token) throws Exception {
        log.debug(token);
        if(usuarioService.activeUser(token)){
            return ResponseEntity.ok();
        }else{
            return ResponseEntity.badRequest();
        }

    }

}
