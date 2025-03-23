package com.example.reward_service.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.interfaces.RSAPublicKey;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter implements Ordered {

    private final RSAKeyProvider rsaKeyProvider;

    public JwtAuthenticationFilter(RSAKeyProvider rsaKeyProvider) {
        this.rsaKeyProvider = rsaKeyProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);

            try {
                // Decode the JWT header to get the `kid`
                DecodedJWT unverifiedJWT = JWT.decode(token);
                String kid = unverifiedJWT.getKeyId(); // Extract `kid` here

                // Fetch the public key using the `kid`
                RSAPublicKey publicKey = rsaKeyProvider.getPublicKey(kid);

                // Verify the token
                Algorithm algorithm = Algorithm.RSA256(publicKey, null);
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(token);

                // Set authentication
                Authentication authentication = new JwtAuthenticationToken(decodedJWT);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid token");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
    @Override
    public int getOrder() {
        // Set the order to run before Spring Security's default filters (e.g., -100)
        return -100;
    }
}