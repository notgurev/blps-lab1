package se.ifmo.blos.lab2.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import se.ifmo.blos.lab2.configs.JwtProperties;

import java.security.Key;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtUtil {
    private final JwtProperties jwtProperties;
    private final Key secretKey;

    @Autowired
    public JwtUtil(final JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecret()));
    }

    public String generateJwtToken(Authentication authentication) {
        var username = authentication.getName();
        var issueDate = Instant.now();

        var claim = new ArrayList<>();
        for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
            String authority = grantedAuthority.getAuthority();
            claim.add(authority);
        }

        final String jwtToken =
                Jwts.builder()
                        .setSubject(username)
                        .claim(jwtProperties.getAuthoritiesClaim(), claim)
                        .setIssuedAt(Date.from(issueDate))
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .compact();

        return jwtToken;
    }

    public Claims getAllClaimsFromToken(final String jwtToken) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody();
    }

    @SuppressWarnings("unchecked")
    public Collection<GrantedAuthority> getAuthorities(final String token) {
        final var claims = getAllClaimsFromToken(token);
        final var authorities = (List<String>) claims.get(jwtProperties.getAuthoritiesClaim());
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    public String getUsername(final String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public Date getExpirationDate(final String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    public boolean isTokenExpired(final String token) {
        final var expirationTime = getExpirationDate(token);
        return expirationTime.before(new Date());
    }

    public boolean isTokenValid(final String token) {
        return !isTokenExpired(token);
    }
}
