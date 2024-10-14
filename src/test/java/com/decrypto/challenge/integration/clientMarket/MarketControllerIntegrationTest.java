package com.decrypto.challenge.integration.clientMarket;

/**
 * @Author dbenitez
 */
import com.decrypto.challenge.clientMarket.dtos.MarketCountryDTO;
import com.decrypto.challenge.clientMarket.dtos.MarketDTO;
import com.decrypto.challenge.clientMarket.services.interfaces.IMarketService;
import com.decrypto.challenge.common._core.utils.MessageUtils;
import com.decrypto.challenge.country.dtos.CountryDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Locale;

import static com.decrypto.challenge.common._core.utils.AppUtils.convertToJson;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class MarketControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IMarketService marketService;

    private final String BASE_URL = "/market/v1/markets";

    @BeforeEach
    void setUp() {
        Locale.setDefault(Locale.of("es", "AR"));
    }

    @Test
    void givenValidMarket_whenCreate_thenStatusCreated() throws Exception {
        MarketDTO marketDto = new MarketDTO();
        marketDto.setCode("ROFEX-test");
        marketDto.setCountry(new MarketCountryDTO("Argentina"));

        this.mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(marketDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.meta.message").value(MessageUtils.searchMessage("generic.created")))
                .andDo(print());
    }

    @Test
    void givenExistingMarket_whenCreate_thenStatusBadRequest() throws Exception {
        MarketDTO marketDto = new MarketDTO();
        marketDto.setCode("ROFEX");
        marketDto.setCountry(new MarketCountryDTO("Argentina"));

        this.mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(marketDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.meta.message").value(MessageUtils.searchMessage("generic.alreadyExist")))
                .andDo(print());
    }

    @Test
    void givenMarketWithoutCountry_whenCreate_thenStatusBadRequest() throws Exception {
        MarketDTO marketDto = new MarketDTO();
        marketDto.setCode("ROFEX");

        this.mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(marketDto)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void givenMarketWithEmptyCode_whenCreate_thenStatusBadRequest() throws Exception {
        MarketDTO marketDto = new MarketDTO();
        marketDto.setCode("");
        marketDto.setCountry(new MarketCountryDTO("Argentina"));

        this.mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(marketDto)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void givenMarketWithNullCode_whenCreate_thenStatusBadRequest() throws Exception {
        MarketDTO marketDto = new MarketDTO();
        marketDto.setCountry(new MarketCountryDTO("Argentina"));

        this.mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(marketDto)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void givenInvalidCountry_whenCreate_thenStatusBadRequest() throws Exception {
        MarketDTO marketDto = new MarketDTO();
        marketDto.setCode("INVALIDO");
        marketDto.setCountry(new MarketCountryDTO("INVALIDO"));

        this.mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(marketDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.meta.message").value(MessageUtils.searchMessage("country.notExist")))
                .andDo(print());
    }
}

