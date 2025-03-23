package com.example.reward_service.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class JwtAuthenticationToken implements Authentication {

    private final DecodedJWT decodedJWT;
    private boolean authenticated;

    public JwtAuthenticationToken(DecodedJWT decodedJWT) {
        this.decodedJWT = decodedJWT;
        this.authenticated = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public Object getCredentials() {
        return decodedJWT.getToken();
    }

    @Override
    public Object getDetails() {
        return decodedJWT;
    }

    @Override
    public Object getPrincipal() {
        return decodedJWT.getSubject();
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return decodedJWT.getSubject();
    }
}