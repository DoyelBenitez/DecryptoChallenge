package com.decrypto.challenge.integration.auth;

import com.decrypto.challenge.auth.dtos.RolDTO;
import com.decrypto.challenge.auth.dtos.UserAccountDTO;
import com.decrypto.challenge.auth.services.interfaces.IRolService;
import com.decrypto.challenge.auth.services.interfaces.IUserAccountService;
import com.decrypto.challenge.common._core.exceptions.ServiceExceptionP;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
public class ApiAuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private IUserAccountService userAccountService;
    @Autowired
    private IRolService rolService;

    @BeforeEach
    void setUp() throws ServiceExceptionP {
        RolDTO rolDto = this.rolService.findBy("User");
        assertNotNull(rolDto, "El rol debería existir");
        UserAccountDTO userAccountDto = new UserAccountDTO("usuario_test", "password123", rolDto);
        this.userAccountService.save(userAccountDto);
    }

    @Test
    void givenValidUser_whenSignIn_thenReceiveJwtToken() throws Exception {
        UserAccountDTO userAccountDto = new UserAccountDTO();
        userAccountDto.setUsername("usuario_test");
        userAccountDto.setPassword("password123");

        this.mockMvc.perform(post("/auth/v1/signIn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertToJson(userAccountDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.attributes.token").isNotEmpty())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    private static String convertToJson(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
