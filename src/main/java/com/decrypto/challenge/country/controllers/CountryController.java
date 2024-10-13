package com.decrypto.challenge.country.controllers;

import com.decrypto.challenge.common._core.exceptions.ServiceExceptionP;
import com.decrypto.challenge.common._core.utils.ResponseHttpUtils;
import com.decrypto.challenge.country.dtos.CountryDTO;
import com.decrypto.challenge.country.services.interfaces.ICountryService;
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
@Tag(name = "Paises", description = "API para la gestión de paises")
@AllArgsConstructor
@RestController
@RequestMapping("/country/v1/countries")
public class CountryController {

    @Autowired
    private final ICountryService countryService;

    @Operation(summary = "Crear un nuevo país", description = "Permite crear un nuevo país en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "País creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error de validación o de lógica de negocio", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PostMapping(consumes = "application/json", produces = "application/vnd.api+json")
    public ResponseEntity<?> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del país a crear", required = true,
                    content = @Content(schema = @Schema(implementation = CountryDTO.class)))
            @RequestBody @Valid CountryDTO countryDto) {
        try {
            this.countryService.save(countryDto);
            return ResponseHttpUtils.httpStatusCreated("generic.created");
        } catch (ServiceExceptionP error) {
            return ResponseHttpUtils.httpStatusBadRequest(error.getMessage());
        } catch (Exception error) {
            return ResponseHttpUtils.httpStatusInternalServerError("error.http500");
        }
    }

    @Operation(summary = "Eliminar un país", description = "Marca un país como eliminado en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "País eliminado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error de validación o de lógica de negocio", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @DeleteMapping(path = "/{name}", produces = "application/vnd.api+json")
    public ResponseEntity<?> delete(
            @Parameter(description = "Nombre del país a eliminar", required = true)
            @PathVariable String name) {
        try {
            this.countryService.delete(name);
            return ResponseHttpUtils.httpStatusOK("generic.deleted");
        } catch (ServiceExceptionP error) {
            return ResponseHttpUtils.httpStatusBadRequest(error.getMessage());
        } catch (Exception error) {
            return ResponseHttpUtils.httpStatusInternalServerError("error.http500");
        }
    }

    @Operation(summary = "Actualizar un país", description = "Actualiza los datos de un país existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "País actualizado exitosamente", content = @Content(schema = @Schema(implementation = CountryDTO.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación o de lógica de negocio", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PutMapping(value = "/{name}", consumes = "application/json", produces = "application/vnd.api+json")
    public ResponseEntity<?> update(
            @Parameter(description = "Nombre del país a actualizar", required = true)
            @PathVariable String name,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nombre nuevo del país", required = true,
                    content = @Content(schema = @Schema(implementation = CountryDTO.class)))
            @RequestBody @Valid CountryDTO countryDto) {
        try {
            CountryDTO updatedCountry = this.countryService.update(name, countryDto);
            return ResponseHttpUtils.httpStatusOK(updatedCountry, "generic.updated");
        } catch (ServiceExceptionP error) {
            return ResponseHttpUtils.httpStatusBadRequest(error.getMessage());
        } catch (Exception error) {
            return ResponseHttpUtils.httpStatusInternalServerError("error.http500");
        }
    }

    @Operation(
            summary = "Buscar un país por nombre o ID, o listar todos",
            description = "Permite buscar un país específico por su nombre o ID. Si no se especifica ningún criterio, se listan todos los países."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "País(es) encontrado(s)", content = @Content(schema = @Schema(implementation = CountryDTO.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación o de lógica de negocio", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping(produces = "application/vnd.api+json")
    public ResponseEntity<?> find(
            @Parameter(description = "Nombre del país a buscar", required = false)
            @RequestParam(required = false) String name,

            @Parameter(description = "ID del país a buscar", required = false)
            @RequestParam(required = false) Long id
    ) {
        try {
            if (name != null && !name.isEmpty()) {
                CountryDTO countryDto = this.countryService.find(name);
                return ResponseHttpUtils.httpStatusOK(countryDto);
            } else if (id != null) {
                CountryDTO countryDto = this.countryService.find(id);
                return ResponseHttpUtils.httpStatusOK(countryDto);
            } else {
                List<CountryDTO> countryDtoList = this.countryService.findAll();
                return ResponseHttpUtils.httpStatusOK(countryDtoList);
            }
        } catch (ServiceExceptionP error) {
            return ResponseHttpUtils.httpStatusBadRequest(error.getMessage());
        } catch (Exception error) {
            return ResponseHttpUtils.httpStatusInternalServerError("error.http500");
        }
    }
}
