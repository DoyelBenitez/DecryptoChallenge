package com.decrypto.challenge.integration.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.decrypto.challenge.auth.dtos.RolDTO;
import com.decrypto.challenge.auth.dtos.UserAccountDTO;
import com.decrypto.challenge.auth.services.interfaces.IRolService;
import com.decrypto.challenge.auth.services.interfaces.IUserAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author dbenitez
 */
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class UserAccountServiceIntegrationTest {

    @Autowired
    private IUserAccountService userAccountService;
    @Autowired
    private IRolService rolService;

    @Test
    void givenUserAccount_whenSaved_thenCanBeRetrievedByUsername() throws Exception {
        RolDTO rolDto = this.rolService.findBy("User");
        assertNotNull(rolDto, "El rol debería existir.");
        UserAccountDTO userAccountDto = new UserAccountDTO("usuario_test", "password123", rolDto);
        this.userAccountService.save(userAccountDto);
        UserAccountDTO userAccountDtoRetrieved = this.userAccountService.findByUsername("usuario_test");

        assertNotNull(userAccountDtoRetrieved, "El usuario debería haber sido encontrado.");
        assertThat(userAccountDtoRetrieved.getUsername()).isEqualTo(userAccountDto.getUsername());
        assertThat(userAccountDtoRetrieved.getRole().getId()).isEqualTo(userAccountDto.getRole().getId());
    }
}
