package com.ejercicio.ejercicioPracticas.config;

import com.ejercicio.ejercicioPracticas.persistence.model.UserEntity;
import com.ejercicio.ejercicioPracticas.persistence.repository.IUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final IUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(IUserRepository appUserRepository,
                           PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (!appUserRepository.existsByUsername("admin")) {
            UserEntity admin = new UserEntity();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");

            appUserRepository.save(admin);
        }
    }
}
