package foxgurev.blps.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JwtUtil {
    private final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    private final JwtProperties jwtProperties;
    private final String secretKey;

    @Autowired
    public JwtUtil(final JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.secretKey = "ja5XKcJV478PZ7z91ILt0EYlHieM4clnKzwW1ZdKw";
    }

    public String generateJwtToken(Authentication authentication) {
        UserDetails username = (UserDetails) authentication.getPrincipal();
        var issueDate = Instant.now();


        var claim = new ArrayList<>();
        for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
            String authority = grantedAuthority.getAuthority();
            claim.add(authority);
        }

        final String jwtToken =
                Jwts.builder()
                        .setSubject(username.getUsername())
                        .claim(jwtProperties.getAuthoritiesClaim(), claim)
                        .setIssuedAt(Date.from(issueDate))
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .compact();
        logger.info("Token: " + jwtToken);
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
