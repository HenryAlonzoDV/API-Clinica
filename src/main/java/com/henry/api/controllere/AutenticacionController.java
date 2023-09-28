package com.henry.api.controllere;

import com.henry.api.domain.usuarios.DatosAutenticacionUsuario;
import com.henry.api.domain.usuarios.Usuario;
import com.henry.api.infra.security.DatosJWTToken;
import com.henry.api.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;  // Este metodo nos va a autenticar

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity autenticarUsuario (@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario){
        Authentication authToken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.login(), datosAutenticacionUsuario.password()); // variable TOKEN para pasar nuestros datos de login
        var usuarioAutenticado = authenticationManager.authenticate(authToken);   // variable para pasar el usuario al JWT TOKEN
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());   //generar token JWT para retornar
        return ResponseEntity.ok(new DatosJWTToken(JWTtoken));   // DTO "DatosJWTToken" para devolver el token en un JASON
    }

}
