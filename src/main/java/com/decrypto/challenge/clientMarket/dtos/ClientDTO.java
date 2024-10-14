package com.decrypto.challenge.clientMarket.dtos;

import com.decrypto.challenge.clientMarket._core.mapping.Mapping_Client;
import com.decrypto.challenge.clientMarket.entities.Client;
import com.decrypto.challenge.common._core.jsonApi.anotations.JsonApiRelationships;
import com.decrypto.challenge.common._core.jsonApi.anotations.JsonApiType;
import com.decrypto.challenge.common._core.mapping.IDTOMappable;
import com.decrypto.challenge.common.dto.AbstractDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @Author dbenitez
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representa un comitente en el sistema.")
@JsonApiType("Comitente")
public class ClientDTO extends AbstractDTO implements IDTOMappable<Client> {

    @Schema(description = "La descripción del comitente.", example = "Comitente Principal")
    private String description;

    @Schema(description = "Lista de mercados asociados al comitente.", hidden = true)
    @JsonApiRelationships("mercados")
    private List<MarketDTO> markets;

    @Schema(description = "Lista de códigos de mercado asociados al comitente.", example = "[\"MAE\", \"ROFEX\"]")
    private List<String> marketCodes;

    @Override
    public Client basicMapping() {
        return Mapping_Client.basicMapping(this);
    }

    @Override
    public Client fullMapping() {
        return Mapping_Client.fullMapping(this);
    }
}


