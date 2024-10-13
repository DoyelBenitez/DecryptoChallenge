package com.decrypto.challenge.clientMarket.controllers;

import com.decrypto.challenge.clientMarket.dtos.MarketDTO;
import com.decrypto.challenge.clientMarket.services.interfaces.IMarketService;
import com.decrypto.challenge.common._core.exceptions.ServiceExceptionP;
import com.decrypto.challenge.common._core.utils.ResponseHttpUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author dbenitez
 */
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Market", description = "API para la gestión de mercados")
@AllArgsConstructor
@RestController
@RequestMapping("/market/v1/markets")
public class MarketController {

    @Autowired
    private final IMarketService marketService;

    @Operation(summary = "Crear un nuevo mercado", description = "Permite crear un nuevo mercado en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Mercado creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error de validación o de lógica de negocio", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PostMapping(consumes = "application/json", produces = "application/vnd.api+json")
    public ResponseEntity<?> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del mercado a crear", required = true,
                    content = @Content(schema = @Schema(implementation = MarketDTO.class)))
            @RequestBody @Valid MarketDTO marketDto) {
        try {
            this.marketService.save(marketDto);
            return ResponseHttpUtils.httpStatusCreated("generic.created");
        } catch (ServiceExceptionP error) {
            return ResponseHttpUtils.httpStatusBadRequest(error.getMessage());
        } catch (Exception error) {
            return ResponseHttpUtils.httpStatusInternalServerError("error.http500");
        }
    }

    @Operation(summary = "Eliminar un mercado", description = "Marca un mercado como eliminado en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mercado eliminado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error de validación o de lógica de negocio", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @DeleteMapping(path = "/{code}", produces = "application/vnd.api+json")
    public ResponseEntity<?> delete(
            @Parameter(description = "Código del mercado a eliminar", required = true)
            @PathVariable String code) {
        try {
            this.marketService.delete(code);
            return ResponseHttpUtils.httpStatusOK("generic.deleted");
        } catch (ServiceExceptionP error) {
            return ResponseHttpUtils.httpStatusBadRequest(error.getMessage());
        } catch (Exception error) {
            return ResponseHttpUtils.httpStatusInternalServerError("error.http500");
        }
    }

    @Operation(summary = "Actualizar un mercado", description = "Actualiza los datos de un mercado existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mercado actualizado exitosamente", content = @Content(schema = @Schema(implementation = MarketDTO.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación o de lógica de negocio", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PutMapping(value = "/{code}", consumes = "application/json", produces = "application/vnd.api+json")
    public ResponseEntity<?> update(
            @Parameter(description = "Código del mercado a actualizar", required = true)
            @PathVariable String code,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del mercado actualizados", required = true,
                    content = @Content(schema = @Schema(implementation = MarketDTO.class)))
            @RequestBody @Valid MarketDTO marketDto) {
        try {
            MarketDTO updatedMarketDto = this.marketService.update(code, marketDto);
            return ResponseHttpUtils.httpStatusOK(updatedMarketDto, "generic.updated");
        } catch (ServiceExceptionP error) {
            return ResponseHttpUtils.httpStatusBadRequest(error.getMessage());
        } catch (Exception error) {
            return ResponseHttpUtils.httpStatusInternalServerError("error.http500");
        }
    }

    @Operation(summary = "Buscar un mercado por código o listar todos", description = "Permite buscar un mercado específico por su código o listar todos los mercados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mercado(s) encontrado(s)", content = @Content(schema = @Schema(implementation = MarketDTO.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación o de lógica de negocio", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping(produces = "application/vnd.api+json")
    public ResponseEntity<?> find(
            @Parameter(description = "Código del mercado a buscar", required = false)
            @RequestParam(required = false) String code) {
        try {
            if (code != null && !code.isEmpty()) {
                MarketDTO marketDto = this.marketService.find(code);
                return ResponseHttpUtils.httpStatusOK(marketDto);
            } else {
                List<MarketDTO> marketDtoList = this.marketService.findAll();
                return ResponseHttpUtils.httpStatusOK(marketDtoList);
            }
        } catch (ServiceExceptionP error) {
            return ResponseHttpUtils.httpStatusBadRequest(error.getMessage());
        } catch (Exception error) {
            return ResponseHttpUtils.httpStatusInternalServerError("error.http500");
        }
    }
}

