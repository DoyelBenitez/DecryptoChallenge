package com.decrypto.challenge.auth._core.providers;

import com.decrypto.challenge.auth._core.exceptions.JwtExceptionP;
import com.decrypto.challenge.auth.spring.CustomUserAccount;
import com.decrypto.challenge.common._core.utils.AppUtils;
import com.decrypto.challenge.common._core.utils.JacksonConverterUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;

/**
 * @Author dbenitez
 */
@Component
public class JwtTokenProvider {

    private static String secretKey;
    private static Long validityInMilliseconds;

    @Autowired
    public JwtTokenProvider(@Qualifier("secretKey") String secretKey, @Qualifier("expirationMilliseconds") Long validityInMilliseconds) {
        JwtTokenProvider.secretKey = secretKey;
        JwtTokenProvider.validityInMilliseconds = validityInMilliseconds;
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    protected static SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    /**
     * Genera el token para el usuario recientemente logeado
     * @param usuarioLogeado-
     * @return String
     */
    public static String generateToken(Authentication usuarioLogeado) {
        return generateToken(usuarioLogeado, null, false);
    }

    /**
     * Genera el token para el usuario recientemente logeado con tiempo personalizado
     * @param usuarioLogeado-
     * @return String
     */
    public static String generateToken(Authentication usuarioLogeado, Long customValidityInMilliseconds, Boolean keepTheSession) {
        Map<String,Object> claims = new HashMap<>();
        CustomUserAccount customUserAccount = (CustomUserAccount) usuarioLogeado.getPrincipal();
        customUserAccount.setPassword(null);
        claims.put("principal", customUserAccount);
        claims.put("credentials", usuarioLogeado.getCredentials());
        claims.put("details", usuarioLogeado.getDetails());
        claims.put("authenticated", usuarioLogeado.isAuthenticated());
        claims.put("keepTheSession", keepTheSession);
        return createToken(claims, customValidityInMilliseconds);
    }

    /**
     * Decifra el token que viene en la request y devuelve un Authentication
     * @param request-
     * @return Authentication
     */
    public static Authentication getAuthentication(HttpServletRequest request) throws JsonProcessingException {
        String token = extractToken(request);
        validateToken(token);
        return getAuthentication(token, request);
    }

    /**
     * Extrae el token de la request
     * @param request-
     * @return String
     */
    public static String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer") && bearerToken.length() > 10) {
            return bearerToken.substring(7, bearerToken.length());
        } else if (bearerToken == null) {
            throw new JwtExceptionP("El token recibido es NULL: JwtTokenProvider -> extractToken");
        } else if (!bearerToken.startsWith("Bearer") || bearerToken.length() > 10) {
            throw new JwtExceptionP("El token recibido es INVALIDO: JwtTokenProvider -> extractToken");
        }
        return null;
    }

    /**
     * Valida la integridad del token
     * @param token-
     */
    public static void validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
            if (claims.getPayload().getExpiration().before(new Date())) {
                throw new JwtExceptionP("El tiempo valido para el token expiro: JwtTokenProvider -> validateToken");
            }
        } catch (Exception error) {
            throw new JwtExceptionP("La estructura del token es invalida: JwtTokenProvider -> validateToken");
        }
    }

    /*
     * obtiene el authorities del token
     * @param token-
     * @return List<Object>
    public static List<Object> getAuthorities(String token) {
        return (List<Object>) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("authorities");
    }
     */

    /**
     * Obtene el principal del token
     * @param token-
     * @return Object
     */
    public static Object getPrincipal(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload().get("principal");
    }

    /**
     * Obtiene el CustomUserAccount del token
     * @param token-
     * @return CustomUserAccount
     */
    public static CustomUserAccount getCustomUserAccount(String token) throws JsonProcessingException {
        Object principal = getPrincipal(token);
        return (CustomUserAccount) JacksonConverterUtils.convertTokenJacksonAObjeto(principal, CustomUserAccount.class);
    }

    /**
     * Obtiene el username del token
     * @param token-
     * @return String
     */
    public static String getUsername(String token) throws JsonProcessingException {
        return getCustomUserAccount(token).getUsername();
    }

    /**
     * Crea el token
     * @param claims-
     * @return String
     */
    private static String createToken(Map<String,Object> claims, Long customValidityInMilliseconds) {
        Long validity = validityInMilliseconds;
        if (AppUtils.isNotNullOrEmpty(customValidityInMilliseconds)) {
            validity = customValidityInMilliseconds;
        }
        Date now = new Date();
        Date expiration = new Date(now.getTime() + validity);
        return Jwts
                .builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSigningKey(), Jwts.SIG.HS512)
                .compact();
    }

    /**
     * Genera un Authentication a partir del token
     * @param token-
     * @param request-
     * @return Authentication
     */
    private static Authentication getAuthentication(String token, HttpServletRequest request) throws JsonProcessingException {
        CustomUserAccount principal = getCustomUserAccount(token);
        Collection<? extends SimpleGrantedAuthority> authorities = principal.getAuthorities();
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(principal, "", authorities);
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return usernamePasswordAuthenticationToken;
    }
}
