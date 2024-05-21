package com.ger.backend.usersapp.backendusersapp.auth.filters;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ger.backend.usersapp.backendusersapp.models.entities.User;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
                User user = null;
                String username = null;
                String password = null;
                
                try {
                    user = new ObjectMapper().readValue(request.getInputStream(), User.class);
                    username = user.getUsername();
                    password = user.getPassword();

                    logger.info("Username desde request InputStream (raw)" + username);
                    logger.info("Password desde request InputStream (raw)" + password);
                } catch (StreamReadException e) {
                    
                    e.printStackTrace();
                } catch (DatabindException e) {
                    
                    e.printStackTrace();
                } catch (IOException e) {
                    
                    e.printStackTrace();
                }
                UsernamePasswordAuthenticationToken authToken =new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authToken);
    }
    
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {

    }

}
