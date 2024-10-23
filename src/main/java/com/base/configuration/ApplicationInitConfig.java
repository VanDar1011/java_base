package com.base.configuration;

import com.base.entity.Profile;
import com.base.entity.User;
import com.base.enums.Role;
import com.base.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Configuration
public class ApplicationInitConfig {
    private final PasswordEncoder passwordEncoder;
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
    return args -> {
        if (userRepository.findByName("admin").isEmpty()) {
            var roles = new HashSet<String>();
            roles.add(Role.ADMIN.name());
            Profile profile = new Profile("Duy Tien");
            User user = new User("admin", passwordEncoder.encode("admin"), profile, roles);
            userRepository.save(user);

        }
    };
};
}
