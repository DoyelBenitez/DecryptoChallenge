package com.decrypto.challenge.integration.country.controllers;

import com.decrypto.challenge.common._core.utils.MessageUtils;
import com.decrypto.challenge.country.dtos.CountryDTO;
import com.decrypto.challenge.country.services.interfaces.ICountryService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Locale;

import static com.decrypto.challenge.common._core.utils.AppUtils.convertToJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Author dbenitez
 */
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class CountryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ICountryService countryService;

    private final String BASE_URL = "/country/v1/countries";

    @BeforeEach
    void setUp() {
        Locale.setDefault(Locale.of("es", "AR"));
    }

    /**
     * CREATE
     */
    @Test
    void givenValidCountry_whenCreate_thenStatusCreated() throws Exception {
        CountryDTO countryDto = new CountryDTO();
        countryDto.setName("Argentina-Test");

        this.mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(countryDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.meta.message").value(MessageUtils.searchMessage("generic.created")))
                .andDo(print());
    }

    @Test
    void givenCountryAlreadyExists_whenCreate_thenStatusBadRequest() throws Exception {
        CountryDTO countryDtoDB = new CountryDTO();
        countryDtoDB.setName("Argentina-Test");
        this.countryService.save(countryDtoDB);

        CountryDTO countryDto = new CountryDTO();
        countryDto.setName("ArgEntina-TEst");

        this.mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(countryDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.meta.message").value(MessageUtils.searchMessage("generic.alreadyExist")))
                .andDo(print());
    }

    @Test
    void givenNullCountryDto_whenCreate_thenStatusBadRequest() throws Exception {
        CountryDTO countryDto = new CountryDTO();

        this.mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(countryDto)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    /**
     * DELETE
     */

    @Test
    void givenValidCountryName_whenDelete_thenStatusOK() throws Exception {
        CountryDTO countryDto = new CountryDTO();
        countryDto.setName("Argentina-Test");
        this.countryService.save(countryDto);

        this.mockMvc.perform(delete(this.BASE_URL + "/Argentina-Test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.meta.message").value(MessageUtils.searchMessage("generic.deleted")))
                .andDo(print());
    }

    @Test
    void givenNonExistentCountryName_whenDelete_thenStatusBadRequest() throws Exception {
        this.mockMvc.perform(delete(this.BASE_URL + "/badRequest-Test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.meta.message").value(MessageUtils.searchMessage("generic.notExist")))
                .andDo(print());
    }

    /**
     * UPDATE
     */

    @Test
    void givenValidCountryAndNewName_whenUpdate_thenStatusOK() throws Exception {
        CountryDTO existingCountryDto = new CountryDTO();
        existingCountryDto.setName("Argentina-Test");
        this.countryService.save(existingCountryDto);

        CountryDTO updatedCountryDto = new CountryDTO();
        updatedCountryDto.setName("Argentina-new");

        this.mockMvc.perform(put(this.BASE_URL + "/Argentina-Test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(updatedCountryDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.meta.message").value(MessageUtils.searchMessage("generic.updated")))
                .andExpect(jsonPath("$.data.attributes.name").value("Argentina-new"))
                .andDo(print());
    }

    @Test
    void givenNonExistentCountry_whenUpdate_thenStatusBadRequest() throws Exception {
        CountryDTO countryDto = new CountryDTO();
        countryDto.setName("NonExistentCountry");

        this.mockMvc.perform(put(BASE_URL + "/NonExistentCountry")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(countryDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.meta.message").value(MessageUtils.searchMessage("generic.notExist")))
                .andDo(print());
    }

    @Test
    void givenConflictWithExistingCountryName_whenUpdate_thenStatusBadRequest() throws Exception {
        CountryDTO countryDto1 = new CountryDTO();
        countryDto1.setName("Argentina-Test");
        this.countryService.save(countryDto1);

        CountryDTO countryDto2 = new CountryDTO();
        countryDto2.setName("Chile-Test");
        this.countryService.save(countryDto2);

        // Intentar actualizar "Chile-Test" con el nombre de "Argentina-Test"
        countryDto2.setName("Argentina-Test");
        this.mockMvc.perform(put(BASE_URL + "/Chile-Test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(countryDto2)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.meta.message").value(MessageUtils.searchMessage("generic.alreadyExist")))
                .andDo(print());
    }

    @Test
    void givenDeletedCountry_whenUpdate_thenCountryIsRecovered() throws Exception {
        CountryDTO countryDto1 = new CountryDTO();
        countryDto1.setName("Argentina-Test");
        this.countryService.save(countryDto1);
        this.countryService.delete("Argentina-Test");

        assertThrows(Exception.class, () -> {
            CountryDTO countryDtoDB = this.countryService.find("Argentina-Test");
        }, "No se encontró el país");

        CountryDTO countryDto2 = new CountryDTO();
        countryDto2.setName("Chile-Test");
        this.countryService.save(countryDto2);

        this.mockMvc.perform(put(BASE_URL + "/Chile-Test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(countryDto1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.meta.message").value(MessageUtils.searchMessage("generic.updated")))
                .andDo(print());

        CountryDTO countryDtoDB = this.countryService.find("Argentina-Test");
        assertThat(countryDtoDB).isNotNull();
    }

    /**
     * READ
     */

    @Test
    void givenValidCountryName_whenFind_thenStatusOK() throws Exception {
        CountryDTO countryDto = new CountryDTO();
        countryDto.setName("Argentina-Test");
        this.countryService.save(countryDto);

        this.mockMvc.perform(get(BASE_URL + "?name=Argentina-Test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.attributes.name").value("Argentina-test"))
                .andDo(print());
    }

    @Test
    void whenFindAllCountries_thenStatusOK() throws Exception {
        List<CountryDTO> countryDtoList = this.countryService.findAll();

        CountryDTO countryDto1 = new CountryDTO();
        countryDto1.setName("Argentina-Test");
        this.countryService.save(countryDto1);

        CountryDTO countryDto2 = new CountryDTO();
        countryDto2.setName("Chile-Test");
        this.countryService.save(countryDto2);

        this.mockMvc.perform(get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(countryDtoList.size() + 2))
                .andDo(print());
    }

}

