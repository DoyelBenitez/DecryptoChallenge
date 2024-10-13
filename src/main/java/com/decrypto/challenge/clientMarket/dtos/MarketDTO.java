package com.decrypto.challenge.clientMarket.dtos;

import com.decrypto.challenge.clientMarket._core.mapping.Mapping_Market;
import com.decrypto.challenge.clientMarket.entities.Market;
import com.decrypto.challenge.common._core.jsonApi.anotations.JsonApiRelationships;
import com.decrypto.challenge.common._core.jsonApi.anotations.JsonApiType;
import com.decrypto.challenge.common._core.mapping.IDTOMappable;
import com.decrypto.challenge.common.dto.AbstractDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

/**
 * @Author dbenitez
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representa un mercado en el sistema.")
@JsonApiType("Mercado")
public class MarketDTO extends AbstractDTO implements IDTOMappable<Market> {

    @Schema(description = "Código único del mercado.", example = "ROFEX")
    @NotBlank(message = "El código del mercado es obligatorio.")
    private String code;

    @Schema(description = "Descripción del mercado.", example = "Mercado a Término de Rosario")
    private String description;

    @Schema(description = "Información del país asociado al mercado.")
    @JsonApiRelationships("Pais")
    @NotNull(message = "El país es obligatorio.")
    private MarketCountryDTO country;

    @Schema(description = "Lista de clientes asociados al mercado. Ignorado en la serialización JSON.", hidden = true)
    @JsonApiRelationships("Clientes")
    private List<ClientDTO> clients;

    @Override
    public Market basicMapping() {
        return Mapping_Market.basicMapping(this);
    }

    @Override
    public Market fullMapping() {
        return Mapping_Market.fullMapping(this);
    }
}

