package org.chassot.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.chassot.auth.model.Usuario;
import org.chassot.auth.model.dto.ClientCredential;
import org.chassot.auth.model.dto.NewUser;
import org.chassot.auth.model.dto.UserLoggedIn;
import org.chassot.auth.services.JwtTokenService;
import org.chassot.auth.services.JwtUserDetailsService;
import org.chassot.auth.services.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
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
    public ResponseEntity<UserLoggedIn> authenticate(@RequestBody ClientCredential clientCredential) throws Exception {
        try{
            final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(clientCredential.getUid());

            if(clientCredential.getSecret().equals(userDetails.getPassword())){
                final String token = jwtTokenService.generateToken(userDetails);
                return ResponseEntity.ok(new UserLoggedIn(userDetails, token, null));
            }else{
                return ResponseEntity.ok(new UserLoggedIn(userDetails, null, "password validation failed"));
            }
        }catch (UsernameNotFoundException ex){
            return ResponseEntity.ok(new UserLoggedIn(null, null, "user not found"));
        }
    }

    @PostMapping(path = "register")
    public ResponseEntity<UserLoggedIn> register(@RequestBody NewUser newUser) throws Exception {
        Usuario usuario = modelMapper.map(newUser, Usuario.class);
        log.debug(usuario.toString());
        Usuario usuarioSalvo = usuarioService.save(usuario);

        UserLoggedIn retorno = new UserLoggedIn();
        retorno.setUser(modelMapper.map(jwtUserDetailsService.loadUserByUsername(usuarioSalvo.getEmail()), UserDetails.class));
        retorno.setToken(jwtTokenService.generateToken(
                jwtUserDetailsService.loadUserByUsername(usuarioSalvo.getEmail())));
        return ResponseEntity.ok(retorno);
    }

    @GetMapping(path = "loadsession")
    public ResponseEntity<UserLoggedIn> loadSession(@RequestHeader(value = "Authorization") String token) throws Exception {
        try{
            String userEmail = jwtTokenService.getUsernameFromToken(StringUtils.split(token, " ")[1]);
            UserDetails user = jwtUserDetailsService.loadUserByUsername(userEmail);
            return ResponseEntity.ok(new UserLoggedIn(user, token, null));
        } catch (Exception ex){
            return ResponseEntity.badRequest().body(new UserLoggedIn(null, null, ex.getMessage()));
        }

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
