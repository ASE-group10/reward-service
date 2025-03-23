package com.example.reward_service.security;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.security.interfaces.RSAPublicKey;

@Component
public class RSAKeyProvider {

    private static final String AUTH0_JWKS_URL = "https://sustainable-wayfinding.eu.auth0.com/";

    public RSAPublicKey getPublicKey(String kid) {
        try {
            JwkProvider provider = new JwkProviderBuilder(new URL(AUTH0_JWKS_URL)).build();
            Jwk jwk = provider.get(kid); // Use the extracted `kid`
            return (RSAPublicKey) jwk.getPublicKey();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving public key from Auth0", e);
        }
    }
}