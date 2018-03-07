package xyz.ndlr;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import xyz.ndlr.model.entity.User;
import xyz.ndlr.security.auth.Role;

import java.util.Arrays;

@TestConfiguration
public class SpringSecurityWebAuxTestConfig {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@ndlr.xyz");
        admin.setRole(Role.ADMIN);
        admin.setPassword("password");

        User basicUser = new User();
        basicUser.setUsername("user");
        basicUser.setEmail("user@ndlr.xyz");
        basicUser.setRole(Role.USER);
        basicUser.setPassword("user");

        return new InMemoryUserDetailsManager(Arrays.asList(
                admin, basicUser
        ));
    }
}