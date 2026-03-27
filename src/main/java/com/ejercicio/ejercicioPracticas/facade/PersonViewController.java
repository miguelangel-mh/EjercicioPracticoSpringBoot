package com.ejercicio.ejercicioPracticas.facade;

import com.ejercicio.ejercicioPracticas.business.PersonBusiness;
import com.ejercicio.ejercicioPracticas.dto.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/personas-view")
public class PersonViewController {

    private final PersonBusiness personBusiness;

    public PersonViewController(final PersonBusiness personBusiness) {
        this.personBusiness = personBusiness;
    }

    @GetMapping
    public String showPersonsTable(Model model) {
        List<PersonDto> persons = personBusiness.findAllPersons();
        model.addAttribute("persons", persons);
        return "person-view";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("personForm", new PersonFormDto());
        model.addAttribute("editMode", false);
        model.addAttribute("currentDni", "");
        return "person-form";
    }

    @PostMapping
    public String createPerson(@Valid @ModelAttribute("personForm") PersonFormDto personFormDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("editMode", false);
            model.addAttribute("currentDni", "");
            return "person-form";
        }

        CreatePersonRequestDto requestDto = buildCreateRequest(personFormDto);
        personBusiness.createPerson(requestDto);
        return "redirect:/personas-view";
    }

    @GetMapping("/edit/{dni}")
    public String showUpdateForm(@PathVariable String dni, Model model) {

        Optional<PersonDto> personDtoOptional = personBusiness.findPersonByDni(dni);

        if (personDtoOptional.isEmpty()) {
            return "redirect:/person-view";
        }

        PersonFormDto personFormDto = buildPersonForm(personDtoOptional.orElseThrow());

        model.addAttribute("personForm", personFormDto);
        model.addAttribute("editMode", true);
        model.addAttribute("currentDni", dni);

        return "person-form";
    }

    @PostMapping("/update/{dni}")
    public String updatePerson(@PathVariable String dni, @Valid @ModelAttribute("personForm") PersonFormDto personFormDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("editMode", true);
            model.addAttribute("currentDni", dni);
            return "person-form";
        }

        UpdatePersonRequestDto requestDto = buildUpdateRequest(personFormDto);
        personBusiness.updatePersonByDni(dni, requestDto);

        return "redirect:/personas-view";
    }

    @PostMapping("/delete/{dni}")
    public String deletePerson(@PathVariable String dni) {
        personBusiness.deletePersonByDni(dni);
        return "redirect:/personas-view";
    }

    @PostMapping("/delete-all")
    public String deleteAllPersons() {
        personBusiness.deleteAllPersons();
        return "redirect:/personas-view";
    }

    private CreatePersonRequestDto buildCreateRequest(final PersonFormDto personFormDto) {
        ContactDetailsDto contactDetailsDto = ContactDetailsDto.builder()
                .telephone(personFormDto.getTelephone())
                .street(personFormDto.getStreet())
                .email(personFormDto.getEmail())
                .build();

        return CreatePersonRequestDto.builder()
                .dni(personFormDto.getDni())
                .name(personFormDto.getName())
                .lastName(personFormDto.getLastName())
                .contactDetails(contactDetailsDto)
                .build();
    }

    private UpdatePersonRequestDto buildUpdateRequest(final PersonFormDto personFormDto) {
        ContactDetailsDto contactDetailsDto = ContactDetailsDto.builder()
                .telephone(personFormDto.getTelephone())
                .street(personFormDto.getStreet())
                .email(personFormDto.getEmail())
                .build();

        return UpdatePersonRequestDto.builder()
                .name(personFormDto.getName())
                .lastName(personFormDto.getLastName())
                .contactDetails(contactDetailsDto)
                .build();
    }

    private PersonFormDto buildPersonForm(final PersonDto personDto) {
        PersonFormDto personFormDto = new PersonFormDto();
        personFormDto.setDni(personDto.getDni());
        personFormDto.setName(personDto.getName());
        personFormDto.setLastName(personDto.getLastName());
        personFormDto.setTelephone(personDto.getContactDetails().getTelephone());
        personFormDto.setStreet(personDto.getContactDetails().getStreet());
        personFormDto.setEmail(personDto.getContactDetails().getEmail());
        return personFormDto;
    }

}
