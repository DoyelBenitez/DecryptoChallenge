package com.decrypto.challenge.auth.controllers;

import com.decrypto.challenge.auth.dtos.UserAccountDTO;
import com.decrypto.challenge.auth.services.interfaces.IAuthService;
import com.decrypto.challenge.common._core.exceptions.ServiceExceptionP;
import com.decrypto.challenge.common._core.utils.ResponseHttpUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author dbenitez
 */
@Tag(name = "Autenticación", description = "API para recuperar el token de autenticación. Las credenciales son las mismas que se usaron para entrar a Swagger")
@RestController
@RequestMapping("/auth")
public class ApiAuthController {

    @Autowired
    private IAuthService authService;

    @Operation(summary = "Iniciar sesión", description = "Autentica y devuelve un token JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticación exitosa",
                    content = @Content(mediaType = "application/vnd.api+json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request - Datos de entrada inválidos",
                    content = @Content(mediaType = "application/vnd.api+json")),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Credenciales incorrectas",
                    content = @Content(mediaType = "application/vnd.api+json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/vnd.api+json"))
    })
    @PostMapping(path = "v1/signIn", consumes = "application/json",  produces = "application/vnd.api+json")
    public ResponseEntity<?> signIn(@RequestBody @Valid UserAccountDTO userAccountDto) {
        try {
            Map<String, Object> map = this.authService.signIn(userAccountDto);
            return ResponseHttpUtils.httpStatusOK(map,"generic.welcome");
        } catch (ServiceExceptionP error) {
            return ResponseHttpUtils.httpStatusBadRequest(error.getMessage());
        } catch (AuthenticationException error)  {
            return ResponseHttpUtils.httpStatusUnauthorized("generic.wrongCredentials");
        } catch (Exception error) {
            return ResponseHttpUtils.httpStatusInternalServerError("error.http500");
        }
    }
}
