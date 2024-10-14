package com.decrypto.challenge.integration.auth;

import com.decrypto.challenge.auth.dtos.RolDTO;
import com.decrypto.challenge.auth.services.interfaces.IRolService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @Author dbenitez
 */
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class RolServicesIntegrationTest {

    @Autowired
    private IRolService rolService;

    @Test
    void givenRol_whenSaved_thenCanBeRetrievedByName() throws Exception {
        RolDTO rolDto = new RolDTO("User-test");
        this.rolService.save(rolDto);

        RolDTO rolDtoRetrieved = this.rolService.findBy(rolDto.getName());
        assertNotNull(rolDtoRetrieved, "El rol debería haber sido encontrado");
        assertThat(rolDtoRetrieved.getName()).isEqualTo(rolDto.getName());
    }
}
