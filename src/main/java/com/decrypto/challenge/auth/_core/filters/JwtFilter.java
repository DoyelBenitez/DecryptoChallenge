package com.decrypto.challenge.auth._core.filters;

import com.decrypto.challenge.auth._core.exceptions.JwtExceptionP;
import com.decrypto.challenge.auth._core.providers.JwtTokenProvider;
import com.decrypto.challenge.common._core.utils.JacksonConverterUtils;
import com.decrypto.challenge.common._core.utils.ResponseHttpUtils;
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
import java.io.PrintWriter;
import java.util.List;

/**
 * @Author dbenitez
 */

@Slf4j
public class JwtFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        log.info("Method: {}", request.getMethod());
        log.info("Request: {}", request.getRequestURL());
        try {
            Authentication authentication = JwtTokenProvider.getAuthentication(request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(req, res);
        } catch (JwtExceptionP error) {
            this.generateResponse(response, "generic.invalidToken", HttpStatus.UNAUTHORIZED);
        } catch (Exception error) {
            this.generateResponse(response, "error.http500", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Genera la respuesta HTTP
     *
     * @param response-
     * @param keyMsj-
     * @throws IOException-
     */
    private void generateResponse(HttpServletResponse response, String keyMsj, HttpStatus state) throws IOException {
        ResponseEntity<?> responseEntity = null;
        if (keyMsj != null) {
            responseEntity = switch (state) {
                case HttpStatus.BAD_REQUEST -> ResponseHttpUtils.httpStatusBadRequest(keyMsj);
                case HttpStatus.UNAUTHORIZED -> ResponseHttpUtils.httpStatusUnauthorized(keyMsj);
                case HttpStatus.INTERNAL_SERVER_ERROR -> ResponseHttpUtils.httpStatusInternalServerError(keyMsj);
                default -> ResponseHttpUtils.httpStatusBadRequest(keyMsj);
            };
        }
        assert responseEntity != null;
        log.info("JSON Response: {}", responseEntity.getBody());
        response.setStatus(state.value());
        response.setContentType("application/vnd.api+json");
        PrintWriter out = response.getWriter();
        out.print(responseEntity.getBody());
        out.flush();
    }

}
