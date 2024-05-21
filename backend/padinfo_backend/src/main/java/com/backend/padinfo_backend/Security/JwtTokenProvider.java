package com.backend.padinfo_backend.Security;

import com.backend.padinfo_backend.model.entity.UserInfo;
import com.backend.padinfo_backend.model.service.UserInfo.IUserInfoService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenProvider {
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    @Autowired
    private IUserInfoService userInfoService;

    private final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    // Naturaleza del Token!!!
    public static final String TOKEN_HEADER = "Authorization"; // Encabezado
    public static final String TOKEN_PREFIX = "Bearer "; // Prefijo, importante este espacio
    public static final String TOKEN_TYPE = "JWT"; // Tipo de Token

    public String extractUsername(String token)
    {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserInfo userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserInfo userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    public long getExpirationTime() {
        return jwtExpiration;
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserInfo userDetails,
            long expiration
    )
    {
        return Jwts
                .builder()
                //.setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                //.claim("roles", roles)
                /*.claim("roles", userDetails.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.joining(", "))
                )*/
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails)
    {
        /*
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseClaimsJws(token);
        }
        catch (SignatureException ex) {
            logger.info("Error en la firma del token JWT: " + ex.getMessage());
        } catch (MalformedJwtException ex) {
            logger.info("Token malformado: " + ex.getMessage());
        } catch (ExpiredJwtException ex) {
            logger.info("El token ha expirado: " + ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            logger.info("Token JWT no soportado: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            logger.info("JWT claims vacío");
        }*/


        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
