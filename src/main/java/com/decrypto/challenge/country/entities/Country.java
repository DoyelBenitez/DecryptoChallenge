package com.decrypto.challenge.country.entities;

import com.decrypto.challenge.common._core.mapping.IEntityMappable;
import com.decrypto.challenge.common.entities.AbstractEntity;
import com.decrypto.challenge.country._core.mapping.Mapping_Country;
import com.decrypto.challenge.country.dtos.CountryDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

/**
 * @Author dbenitez
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Country extends AbstractEntity implements IEntityMappable<CountryDTO> {

    @Column(nullable = false, unique = true)
    private String name;

    @Override
    public CountryDTO basicMapping() {
        return Mapping_Country.basicMapping(this);
    }

    @Override
    public CountryDTO fullMapping() {
        return Mapping_Country.fullMapping(this);
    }
}
