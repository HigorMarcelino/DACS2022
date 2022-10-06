package br.univille.apidacs2022.api;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.univille.apidacs2022.security.JWTUtil;
import br.univille.coredacs2022.entity.User;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @Autowired
    private UserDetailsService serviceMyUserDetail;
    @Autowired
    private JWTUtil serviceJWT;

    private Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping("/signin")
    public ResponseEntity signin(@RequestBody User usuario){
        UserDetails userDetails = serviceMyUserDetail.loadUserByUsername(usuario.getUser());
        if(userDetails.getPassword().equals(usuario.getSenha())){
            String token = serviceJWT.generateToken(userDetails);
            logger.info("Usuário autenticado: " + usuario.getUser());
            return ResponseEntity.ok(token);    
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }


}