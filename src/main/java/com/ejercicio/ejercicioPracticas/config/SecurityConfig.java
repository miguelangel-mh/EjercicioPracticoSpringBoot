package com.ejercicio.ejercicioPracticas.config;

import com.ejercicio.ejercicioPracticas.persistence.repository.IUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth

                        // Páginas públicas
                        .requestMatchers("/", "/personas-view", "/login", "/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/register").permitAll()

                        // Recursos estáticos
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()

                        // Consultas públicas de la API, si la tienes
                        .requestMatchers(HttpMethod.GET, "/personas", "/personas/**").permitAll()

                        // Vistas protegidas
                        .requestMatchers(HttpMethod.GET, "/personas-view/new").authenticated()
                        .requestMatchers(HttpMethod.GET, "/personas-view/edit/**").authenticated()

                        // Crear y modificar: cualquier usuario logueado
                        .requestMatchers(HttpMethod.POST, "/personas-view").authenticated()
                        .requestMatchers(HttpMethod.POST, "/personas-view/update/**").authenticated()

                        // Borrar: solo ADMIN
                        .requestMatchers(HttpMethod.POST, "/personas-view/delete/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/personas-view/delete-all").hasRole("ADMIN")

                        // API protegida para crear/modificar, si la tienes
                        .requestMatchers(HttpMethod.POST, "/personas/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/personas/**").authenticated()

                        // API borrar: solo ADMIN
                        .requestMatchers(HttpMethod.DELETE, "/personas/**").hasRole("ADMIN")

                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/personas-view", true)
                        .failureUrl("/personas-view?loginError=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/personas-view?logout=true")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(IUserRepository appUserRepository) {
        return username -> appUserRepository.findByUsername(username)
                .map(appUser -> User.builder()
                        .username(appUser.getUsername())
                        .password(appUser.getPassword())
                        .roles(appUser.getRole())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
