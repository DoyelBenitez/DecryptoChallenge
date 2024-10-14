package com.decrypto.challenge.clientMarket.dtos;

import com.decrypto.challenge.common.dto.AbstractDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * @Author dbenitez
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representa las estadísticas del sistema.")
public class StatsDTO extends AbstractDTO {

    @Schema(description = "Nombre del país al que pertenecen las estadísticas.", example = "Argentina")
    private String country;

    @Schema(description = "Lista de mercados con sus respectivas estadísticas. Cada elemento contiene un mapa que relaciona diferentes métricas del mercado con sus valores.")
    private List<Map<String, Map<String, Double>>> market;
}

