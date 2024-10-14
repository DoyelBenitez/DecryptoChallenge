package com.decrypto.challenge.clientMarket.controllers;

import com.decrypto.challenge.clientMarket.dtos.StatsDTO;
import com.decrypto.challenge.clientMarket.services.interfaces.IStatsService;
import com.decrypto.challenge.common._core.exceptions.ServiceExceptionP;
import com.decrypto.challenge.common._core.utils.JacksonConverterUtils;
import com.decrypto.challenge.common._core.utils.ResponseHttpUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author dbenitez
 */
@SecurityRequirement(name = "bearerAuth")
@Tag(description = "Estadísticas", name = "API para visualizar las estadísticas del sistema")
@AllArgsConstructor
@RestController
@RequestMapping("/stat/v1/stats")
public class StatsController {

    @Autowired
    private final IStatsService statsService;

    @Operation(
            summary = "Obtener estadísticas",
            description = "Este endpoint permite obtener una lista de estadísticas del sistema. Las estadísticas incluyen datos relevantes que se presentan en formato JSON."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Estadísticas obtenidas exitosamente",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = StatsDTO.class)))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error de validación o de lógica de negocio",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping(produces = "application/json")
    public ResponseEntity<?> find() {
        try {
            List<StatsDTO> statsDTOList = this.statsService.getStatsData();
            return ResponseEntity.ok(JacksonConverterUtils.toJson(statsDTOList));
        } catch (ServiceExceptionP error) {
            return ResponseHttpUtils.httpStatusBadRequest(error.getMessage());
        } catch (Exception error) {
            return ResponseHttpUtils.httpStatusInternalServerError("error.http500");
        }
    }
}

