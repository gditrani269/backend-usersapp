package com.ger.backend.usersapp.backendusersapp.auth.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtValidationFilter extends BasicAuthenticationFilter  {

    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request, 
        HttpServletResponse response, 
        FilterChain chain)
            throws IOException, ServletException {
        //verifica que en la cabezara del request tenga: "Autorization" y "Bearer "
        String header = request.getHeader(TokenJwtConfig.HEADER_AUTHORIZATION);
        if (header == null || !header.startsWith(TokenJwtConfig.PREFIX_TOKEN)){
            chain.doFilter(request, response);
            return;
        }
        //eliminamos la palabra Bearer de nuestro token
        String token = header.replace(TokenJwtConfig.PREFIX_TOKEN, "");
        //decodificamos la cadena del token para obtenre la frase secreta que debe coincidir con la que tenemos en JwtAuthenticationFilter
        byte[] tokenDecodeBytes = Base64.getDecoder().decode(token);
        //convertimos el arreglo del decode que biene ne bytes a string
        String tokenDecode = new String (tokenDecodeBytes);
        String[] tokenArr = tokenDecode.split(":");
        String secret = tokenArr [0];
        String username = tokenArr [1];
        if (TokenJwtConfig.SECRET_KEY.equalsIgnoreCase(secret)) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } else {
            Map<String,String> body = new HashMap<>();
            body.put("message", "El token JWT no es valido");
            response .getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setStatus(403);
            response.setContentType("application/json");
        }

    }
    

}
