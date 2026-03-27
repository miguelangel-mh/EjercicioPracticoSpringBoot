package com.ejercicio.ejercicioPracticas.facade;

import com.ejercicio.ejercicioPracticas.business.PersonBusiness;
import com.ejercicio.ejercicioPracticas.dto.CreatePersonRequestDto;
import com.ejercicio.ejercicioPracticas.dto.PersonDto;
import com.ejercicio.ejercicioPracticas.dto.UpdatePersonRequestDto;
import com.ejercicio.ejercicioPracticas.persistence.repository.IPersonRepository;
import com.ejercicio.ejercicioPracticas.validate.PersonValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/personas")
@Tag(name = "Personas", description = "Operaciones sobre Personas")
public class PersonController {

    private final PersonBusiness personBusiness;
    private final IPersonRepository personRepository ;

    public PersonController(PersonBusiness personBusiness, IPersonRepository personRepository) {
        this.personBusiness = personBusiness;
        this.personRepository = personRepository ;
    }

    @GetMapping
    @Operation(summary = "Obtenemos todas las personas")
    public ResponseEntity<List<PersonDto>> findAllPersons() {
        List<PersonDto> personDtos = personBusiness.findAllPersons();
        return ResponseEntity.ok(personDtos);
    }

    @GetMapping("/{dni}")
    @Operation(summary = "Obtenemos la persona por su dni")
    public ResponseEntity<PersonDto> findPersonByDni(@PathVariable String dni) {
        Optional<PersonDto> personDtoOptional = personBusiness.findPersonByDni(dni);

        if (personDtoOptional.isPresent()) {
            return ResponseEntity.ok(personDtoOptional.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Crear persona nueva")
    public ResponseEntity<PersonDto> createPerson(@Valid @RequestBody CreatePersonRequestDto requestDto) {

        PersonDto personDto = personBusiness.createPerson(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(personDto);
    }

    @PutMapping("/{dni}")
    @Operation(summary = "Actualizar los datos de una persona por su dni")
    public ResponseEntity<PersonDto> updatePersonByDni(@PathVariable String dni, @Valid @RequestBody UpdatePersonRequestDto requestDto) {

        Optional<PersonDto> personDtoOptional = personBusiness.updatePersonByDni(dni, requestDto);

        if (personDtoOptional.isPresent()) {
            return ResponseEntity.ok(personDtoOptional.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{dni}")
    @Operation(summary = "Eliminamos la persona por su dni")
    public ResponseEntity<Void> deletePersonByDni(@PathVariable String dni) {
        boolean deleted = personBusiness.deletePersonByDni(dni);

        if (deleted) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping
    @Operation(summary = "Eliminamos todas las personas")
    public ResponseEntity<Void> deleteAllPersons() {
        personBusiness.deleteAllPersons();
        return ResponseEntity.noContent().build();
    }

}
