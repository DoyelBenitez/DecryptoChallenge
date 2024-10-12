package com.decrypto.challenge.auth._core.configs;
import com.decrypto.challenge.auth.dao.interfaces.IRolDAO;
import com.decrypto.challenge.auth.dao.interfaces.IUserAccountDAO;
import com.decrypto.challenge.auth.dtos.RolDTO;
import com.decrypto.challenge.auth.dtos.UserAccountDTO;
import com.decrypto.challenge.common._core.exceptions.ServiceExceptionP;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author dbenitez
 */
@AllArgsConstructor
@Configuration
public class DataInitializerConfig {

    @Autowired
    private final IUserAccountDAO userAccountDao;
    @Autowired
    private final IRolDAO rolDao;

    @Bean
    public CommandLineRunner initDatabase() {
        return args -> {
            createRolIfNotExists("Admin");
            createRolIfNotExists("User");
            createUserIfNotExists("decrypto", "$2a$12$ofBYa2AJ0vx3WStrW56qeOJwqj/0YN1k.JYjy46O6CvPOqqzHUV4G", "Admin");
            createUserIfNotExists("user", "$2a$12$ofBYa2AJ0vx3WStrW56qeOJwqj/0YN1k.JYjy46O6CvPOqqzHUV4G", "User");
        };
    }

    private void createRolIfNotExists(String name) {
        if (!this.rolDao.existsBy(name)) {
            RolDTO rolDto = new RolDTO();
            rolDto.setName(name);
            this.rolDao.save(rolDto);
            System.out.println("El rol " + name + " creado.");
        } else {
            System.out.println("El rol " + name + " ya existe.");
        }
    }

    private void createUserIfNotExists(String username, String password, String role) {
        if (!this.userAccountDao.existBy(username)) {
            RolDTO rolDto = this.rolDao.findBy(role);
            UserAccountDTO UserAccountDto = new UserAccountDTO();
            UserAccountDto.setUsername(username);
            UserAccountDto.setPassword(password);
            UserAccountDto.setRole(rolDto);
            this.userAccountDao.save(UserAccountDto);
            System.out.println("El usuario " + username + " creado.");
        } else {
            System.out.println("El usuario " + username + " ya existe.");
        }
    }
}
