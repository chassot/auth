package org.chassot.auth.controller;


import org.chassot.auth.model.Usuario;
import org.chassot.auth.model.dto.User;
import org.chassot.auth.repository.UsuarioRepo;
import org.chassot.auth.services.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("api/user")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepo usuarioRepo;

    ModelMapper modelMapper = new ModelMapper();

    @GetMapping(path = "{uid}")
    public ResponseEntity<?> getUser(@PathVariable String uid) throws Exception {
        Usuario usuario = usuarioRepo.findOne(UUID.fromString(uid));
        User userDTO = modelMapper.map(usuario, User.class);
        return ResponseEntity.ok().body(userDTO);
    }

}
