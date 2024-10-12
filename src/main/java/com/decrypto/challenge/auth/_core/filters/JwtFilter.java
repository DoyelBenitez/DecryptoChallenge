package com.decrypto.challenge.auth._core.filters;

import com.decrypto.challenge.auth._core.providers.JwtTokenProvider;
import com.decrypto.challenge.common._core.utils.JacksonConverterUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.List;

/**
 * @Author dbenitez
 */

@Slf4j
public class JwtFilter extends GenericFilterBean {

    private final List<String> rutasPermitidasSinToken = List.of(
            "/decrypto/api"
    );

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        log.info("Method: {}", request.getMethod());
        log.info("Request: {}", request.getRequestURL());
        if (/*!this.accessURLWithoutToken(request)*/ false) {
            try {
                Authentication authentication = JwtTokenProvider.getAuthentication(request);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(req, res);
            } catch (Exception error) {
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            filterChain.doFilter(req, res);
        }
    }

    /**
     * Funcion que se encarga de filtrar los endpoints que pueden pasar sin token
     *
     * @param httpRequest-
     * @return True, si la url no requiere token
     */
    private Boolean accessURLWithoutToken(HttpServletRequest httpRequest) {
        if (httpRequest.getMethod().equals("OPTIONS")) {
            return true;
        }
        for (String ruta: this.rutasPermitidasSinToken) {
            if (httpRequest.getRequestURI().equals(ruta)) {
                return true;
            }
        }
        return false;
    }

}
