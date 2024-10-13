package com.decrypto.challenge.unit.country;

import com.decrypto.challenge.common._core.exceptions.ServiceExceptionP;
import com.decrypto.challenge.common._core.utils.AppUtils;
import com.decrypto.challenge.common._core.utils.MessageUtils;
import com.decrypto.challenge.country.dtos.CountryDTO;
import com.decrypto.challenge.country.services.interfaces.ICountryService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Locale;

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
public class CountryControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ICountryService countryServiceMock;

    private final String BASE_URL = "/country/v1/countries";

    @BeforeEach
    void setUp() {
        Locale.setDefault(Locale.of("es", "AR"));
    }

    /**
     * CREATE
     */

    @Test
    void givenServiceError_whenCreate_thenStatusBadRequest() throws Exception {
        Mockito
                .doThrow(new ServiceExceptionP("generic.null"))
                .when(this.countryServiceMock)
                .save(Mockito.any(CountryDTO.class));

        CountryDTO countryDto = new CountryDTO();
        countryDto.setName("Argentina-Test");

        this.mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(AppUtils.convertToJson(countryDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.meta.message").value(MessageUtils.searchMessage("generic.null")))
                .andDo(print());
    }

    @Test
    void givenServerError_whenCreate_thenStatusInternalServerError() throws Exception {
        Mockito
                .doThrow(new RuntimeException("Error interno del servidor"))
                .when(this.countryServiceMock)
                .save(Mockito.any(CountryDTO.class));

        CountryDTO countryDto = new CountryDTO();
        countryDto.setName("Argentina-Test");

        this.mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(AppUtils.convertToJson(countryDto)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.meta.message").value(MessageUtils.searchMessage("error.http500")))
                .andDo(print());
    }

    /**
     * DELETE
     */

    @Test
    void givenServiceError_whenDelete_thenStatusBadRequest() throws Exception {
        Mockito
                .doThrow(new ServiceExceptionP("generic.null"))
                .when(this.countryServiceMock)
                .delete(Mockito.anyString());

        CountryDTO countryDto = new CountryDTO();
        countryDto.setName("Argentina-Test");

        this.mockMvc.perform(delete(this.BASE_URL + "/Argentina-Test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.meta.message").value(MessageUtils.searchMessage("generic.null")))
                .andDo(print());
    }

    @Test
    void givenServerError_whenDelete_thenStatusInternalServerError() throws Exception {
        Mockito
                .doThrow(new RuntimeException("Error interno del servidor"))
                .when(this.countryServiceMock)
                .delete(Mockito.anyString());

        this.mockMvc.perform(delete(this.BASE_URL + "/Argentina-Test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.meta.message").value(MessageUtils.searchMessage("error.http500")))
                .andDo(print());
    }

    /**
     * READ
     */

    @Test
    void givenServerError_whenFind_thenStatusBadRequest() throws Exception {
        Mockito
                .doThrow(new ServiceExceptionP("generic.null"))
                .when(this.countryServiceMock)
                .find(Mockito.anyString());

        this.mockMvc.perform(get(BASE_URL + "?name=Argentina-Test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.meta.message").value(MessageUtils.searchMessage("generic.null")))
                .andDo(print());
    }

    @Test
    void givenServerError_whenFind_thenStatusInternalServerError() throws Exception {
        Mockito
                .doThrow(new RuntimeException("Error interno del servidor"))
                .when(this.countryServiceMock)
                .find(Mockito.anyString());

        this.mockMvc.perform(get(BASE_URL + "?name=Argentina-Test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.meta.message").value(MessageUtils.searchMessage("error.http500")))
                .andDo(print());
    }

}
