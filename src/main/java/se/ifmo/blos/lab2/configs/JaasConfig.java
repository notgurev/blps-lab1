package se.ifmo.blos.lab2.configs;

import lombok.var;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.jaas.AbstractJaasAuthenticationProvider;
import org.springframework.security.authentication.jaas.AuthorityGranter;
import org.springframework.security.authentication.jaas.DefaultJaasAuthenticationProvider;
import org.springframework.security.authentication.jaas.memory.InMemoryConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import se.ifmo.blos.lab2.repositories.UserRepository;
import se.ifmo.blos.lab2.security.UserDetailsLoginModule;
import se.ifmo.blos.lab2.security.UserRepositoryAuthorityGranter;

import javax.security.auth.login.AppConfigurationEntry;
import java.util.Map;

import static javax.security.auth.login.AppConfigurationEntry.LoginModuleControlFlag.REQUIRED;

@Configuration
public class JaasConfig {

    @Bean
    public javax.security.auth.login.Configuration configuration(
            UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        final var configurationEntries =
                new AppConfigurationEntry[]{
                        new AppConfigurationEntry(
                                UserDetailsLoginModule.class.getCanonicalName(),
                                REQUIRED,
                                Map.of("userDetailsService", userDetailsService, "passwordEncoder", passwordEncoder))
                };
        return new InMemoryConfiguration(Map.of("SPRINGSECURITY", configurationEntries));
    }

    @Bean
    public AbstractJaasAuthenticationProvider jaasAuthenticationProvider(
            javax.security.auth.login.Configuration configuration,
            UserRepository userRepository) {
        final var defaultJaasAuthenticationProvider = new DefaultJaasAuthenticationProvider();
        defaultJaasAuthenticationProvider.setConfiguration(configuration);
        defaultJaasAuthenticationProvider.setAuthorityGranters(
                new AuthorityGranter[]{new UserRepositoryAuthorityGranter(userRepository)});
        return defaultJaasAuthenticationProvider;
    }
}
