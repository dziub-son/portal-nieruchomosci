package pl.twojafirma.nieruchomosci;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.twojafirma.nieruchomosci.model.Role;
import pl.twojafirma.nieruchomosci.model.User;
import pl.twojafirma.nieruchomosci.repository.UserRepository;

import java.util.Set;

@SpringBootApplication
public class NieruchomosciApplication {

    public static void main(String[] args) {
        SpringApplication.run(NieruchomosciApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setEmail("admin@test.pl");
                admin.setRoles(Set.of(Role.ADMIN, Role.USER));
                userRepository.save(admin);
                System.out.println(">>> Utworzono konto administratora: login 'admin', hasło 'admin123' <<<");
            }
        };
    }
}