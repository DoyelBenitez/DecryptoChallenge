package com.decrypto.challenge.unit.auth.services;

import com.decrypto.challenge.auth.dtos.UserAccountDTO;
import com.decrypto.challenge.auth.services.UserAccountService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @Author dbenitez
 */
public class UserAccountServiceUnitTest {

    @InjectMocks
    private UserAccountService userAccountService;

    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    void checkFormatUsername_shouldNotThrowException() throws Exception {
        UserAccountDTO validUser1 = new UserAccountDTO("username1", "password", null);
        UserAccountDTO validUser2 = new UserAccountDTO("user_name.2", "password", null);
        UserAccountDTO validUser3 = new UserAccountDTO("User.Name_3", "password", null);

        Method checkFormatUsername = UserAccountService.class.getDeclaredMethod("checkFormatUsername", UserAccountDTO.class);
        checkFormatUsername.setAccessible(true);

        assertDoesNotThrow(() -> checkFormatUsername.invoke(this.userAccountService, validUser1));
        assertDoesNotThrow(() -> checkFormatUsername.invoke(this.userAccountService, validUser2));
        assertDoesNotThrow(() -> checkFormatUsername.invoke(this.userAccountService, validUser3));
    }

    @Test
    void checkFormatUsername_shouldThrowException() throws Exception {
        UserAccountDTO invalidUser1 = new UserAccountDTO("user!name", "password", null);
        UserAccountDTO invalidUser2 = new UserAccountDTO("user name", "password", null);
        UserAccountDTO invalidUser3 = new UserAccountDTO("username$", "password", null);

        Method checkFormatUsername = UserAccountService.class.getDeclaredMethod("checkFormatUsername", UserAccountDTO.class);
        checkFormatUsername.setAccessible(true);

        assertThrows(Exception.class, () -> checkFormatUsername.invoke(this.userAccountService, invalidUser1));
        assertThrows(Exception.class, () -> checkFormatUsername.invoke(this.userAccountService, invalidUser2));
        assertThrows(Exception.class, () -> checkFormatUsername.invoke(this.userAccountService, invalidUser3));
    }
}
