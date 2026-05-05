package com.ejercicio.ejercicioPracticas.facade;

import com.ejercicio.ejercicioPracticas.dto.RegisterFormDto;
import com.ejercicio.ejercicioPracticas.persistence.model.UserEntity;
import com.ejercicio.ejercicioPracticas.persistence.repository.IUserRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegisterViewController {

    private final IUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterViewController(IUserRepository appUserRepository,
                                  PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String showRegisterForm() {
        return "redirect:/personas-view";
    }

    @PostMapping
    public String registerUser(@Valid @ModelAttribute RegisterFormDto registerFormDto,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "redirect:/personas-view?registerError=true";
        }

        if (!registerFormDto.getPassword().equals(registerFormDto.getRepeatPassword())) {
            return "redirect:/personas-view?registerError=true";
        }

        if (appUserRepository.existsByUsername(registerFormDto.getUsername())) {
            return "redirect:/personas-view?registerError=true";
        }

        UserEntity appUser = new UserEntity();
        appUser.setUsername(registerFormDto.getUsername());
        appUser.setPassword(passwordEncoder.encode(registerFormDto.getPassword()));
        appUser.setRole("USER");

        appUserRepository.save(appUser);

        return "redirect:/personas-view?registered=true";
    }
}