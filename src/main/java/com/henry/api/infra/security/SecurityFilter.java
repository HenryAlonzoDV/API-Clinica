package com.henry.api.infra.security;

import com.henry.api.domain.usuarios.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //OBTENER EL TOKEN DE HEADERS

        var authHeader = request.getHeader("Authorization");
       if (authHeader != null) {
           var token = authHeader.replace("Bearer ", ""); //REPLACE para quedarnos solo con la informacion del token
           var userName = tokenService.getSubject(token);
           if (userName != null) {
                var usuario = usuarioRepository.findByLogin(userName);
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null,
                        usuario.getAuthorities()); // Forzando un inicio de seccion
                SecurityContextHolder.getContext().setAuthentication(authentication);
           }
        }
        filterChain.doFilter(request, response);
    }

}
