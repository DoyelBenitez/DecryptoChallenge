package com.decrypto.challenge.clientMarket.controllers;

import com.decrypto.challenge.clientMarket.services.interfaces.IClientService;
import com.decrypto.challenge.common._core.exceptions.ServiceExceptionP;
import com.decrypto.challenge.common._core.utils.ResponseHttpUtils;
import com.decrypto.challenge.clientMarket.dtos.ClientDTO;
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
@AllArgsConstructor
@RestController
@RequestMapping("/client/v1/clients")
@Tag(description = "Comitente", name = "API para gestionar comitentes")
public class ClientController {

    @Autowired
    private final IClientService clientService;

    @Operation(summary = "Crear un nuevo comitente", description = "Permite crear un nuevo comitente en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comitente creado exitosamente",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación o de lógica de negocio",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content)
    })
    @PostMapping(consumes = "application/json", produces = "application/vnd.api+json")
    public ResponseEntity<?> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del comitente a crear", required = true,
                    content = @Content(schema = @Schema(implementation = ClientDTO.class)))
            @RequestBody @Valid ClientDTO clientDto) {
        try {
            this.clientService.save(clientDto);
            return ResponseHttpUtils.httpStatusCreated("generic.created");
        } catch (ServiceExceptionP error) {
            return ResponseHttpUtils.httpStatusBadRequest(error.getMessage());
        } catch (Exception error) {
            return ResponseHttpUtils.httpStatusInternalServerError("error.http500");
        }
    }

    @Operation(summary = "Eliminar un comitente", description = "Marca un comitente como eliminado en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comitente eliminado exitosamente",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación o de lógica de negocio",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content)
    })
    @DeleteMapping(path = "/{description}", produces = "application/vnd.api+json")
    public ResponseEntity<?> delete(
            @Parameter(description = "Nombre del comitente a eliminar", required = true)
            @PathVariable String description) {
        try {
            this.clientService.delete(description);
            return ResponseHttpUtils.httpStatusOK("generic.deleted");
        } catch (ServiceExceptionP error) {
            return ResponseHttpUtils.httpStatusBadRequest(error.getMessage());
        } catch (Exception error) {
            return ResponseHttpUtils.httpStatusInternalServerError("error.http500");
        }
    }

    @Operation(summary = "Actualizar un comitente", description = "Actualiza los datos de un comitente existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comitente actualizado exitosamente",
                    content = @Content(schema = @Schema(implementation = ClientDTO.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación o de lógica de negocio",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content)
    })
    @PutMapping(value = "/{description}", consumes = "application/json", produces = "application/vnd.api+json")
    public ResponseEntity<?> update(
            @Parameter(description = "Nombre del comitente a actualizar", required = true)
            @PathVariable String description,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nuevos datos del comitente", required = true,
                    content = @Content(schema = @Schema(implementation = ClientDTO.class)))
            @RequestBody @Valid ClientDTO clientDto) {
        try {
            ClientDTO updatedClient = this.clientService.update(description, clientDto);
            return ResponseHttpUtils.httpStatusOK(updatedClient, "generic.updated");
        } catch (ServiceExceptionP error) {
            return ResponseHttpUtils.httpStatusBadRequest(error.getMessage());
        } catch (Exception error) {
            return ResponseHttpUtils.httpStatusInternalServerError("error.http500");
        }
    }

    @Operation(
            summary = "Recuperar un comitente",
            description = "Permite recuperar un comitente marcado como eliminado en el sistema, especificando su descripción."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comitente recuperado exitosamente", content = @Content),
            @ApiResponse(responseCode = "400", description = "Error de validación o de lógica de negocio", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PatchMapping(value = "/{description}", produces = "application/vnd.api+json")
    public ResponseEntity<?> recover(
            @Parameter(description = "Descripción del comitente a recuperar", required = true)
            @PathVariable String description
    ) {
        try {
            this.clientService.recover(description);
            return ResponseHttpUtils.httpStatusOK("generic.recovered");
        } catch (ServiceExceptionP error) {
            return ResponseHttpUtils.httpStatusBadRequest(error.getMessage());
        } catch (Exception error) {
            return ResponseHttpUtils.httpStatusInternalServerError("error.http500");
        }
    }

    @Operation(summary = "Buscar un comitente por nombre o listar todos", description = "Permite buscar un comitente específico por su nombre o listar todos los comitentes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comitente(s) encontrado(s)",
                    content = @Content(schema = @Schema(implementation = ClientDTO.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación o de lógica de negocio",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content)
    })
    @GetMapping(produces = "application/vnd.api+json")
    public ResponseEntity<?> find(
            @Parameter(description = "Nombre del comitente a buscar", required = false)
            @RequestParam(required = false) String description) {
        try {
            if (description != null && !description.isEmpty()) {
                ClientDTO clientDto = this.clientService.find(description);
                return ResponseHttpUtils.httpStatusOK(clientDto);
            } else {
                List<ClientDTO> clientDtoList = this.clientService.findAll();
                return ResponseHttpUtils.httpStatusOK(clientDtoList);
            }
        } catch (ServiceExceptionP error) {
            return ResponseHttpUtils.httpStatusBadRequest(error.getMessage());
        } catch (Exception error) {
            return ResponseHttpUtils.httpStatusInternalServerError("error.http500");
        }
    }
}

