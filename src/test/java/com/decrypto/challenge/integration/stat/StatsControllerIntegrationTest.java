package com.decrypto.challenge.integration.stat;

import com.decrypto.challenge.clientMarket.dtos.ClientDTO;
import com.decrypto.challenge.clientMarket.services.interfaces.IClientService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
public class StatsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private IClientService clientService;

    private final String BASE_URL = "/stat/v1/stats";

    @BeforeEach
    void setUp() {
        Locale.setDefault(Locale.of("es", "AR"));
    }

    @Test
    void givenDataInDb_whenGetStats_thenReturnCorrectStats() throws Exception {
       this.mockMvc.perform(get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].country").value("Argentina"))
                .andExpect(jsonPath("$[0].market[0].MAE.percentage").value(40.0))
                .andExpect(jsonPath("$[0].market[1].ROFEX.percentage").value(30.0))
                .andExpect(jsonPath("$[1].country").value("Uruguay"))
                .andExpect(jsonPath("$[1].market[0].UFEX.percentage").value(30.0))
                .andDo(print());
    }

    @Test
    void givenNewClient_whenGetStats_thenReturnCorrectStats() throws Exception {
        ClientDTO clientDto = new ClientDTO();
        clientDto.setDescription("futures_broker");
        clientDto.setMarketCodes(List.of("UFEX", "ROFEX"));
        this.clientService.save(clientDto);
        this.mockMvc.perform(get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].country").value("Argentina"))
                .andExpect(jsonPath("$[0].market[0].MAE.percentage").value(36.36))
                .andExpect(jsonPath("$[0].market[1].ROFEX.percentage").value(31.82))
                .andExpect(jsonPath("$[1].country").value("Uruguay"))
                .andExpect(jsonPath("$[1].market[0].UFEX.percentage").value(31.82))
                .andDo(print());
    }

}

