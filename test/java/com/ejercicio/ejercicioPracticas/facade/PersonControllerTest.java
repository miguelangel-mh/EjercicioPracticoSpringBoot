package com.ejercicio.ejercicioPracticas.facade;

import com.ejercicio.ejercicioPracticas.business.PersonBusiness;
import com.ejercicio.ejercicioPracticas.dto.ContactDetailsDto;
import com.ejercicio.ejercicioPracticas.dto.CreatePersonRequestDto;
import com.ejercicio.ejercicioPracticas.dto.PersonDto;
import com.ejercicio.ejercicioPracticas.dto.UpdatePersonRequestDto;
import com.ejercicio.ejercicioPracticas.persistence.repository.IPersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

    private static final String DNI = "12345678A";

    @Mock
    private PersonBusiness personBusiness;

    @Mock
    private IPersonRepository personRepository;

    @InjectMocks
    private PersonController personController;

    @Test
    void findAllPersonsTest() {
        PersonDto personDto = createPersonDto();

        when(personBusiness.findAllPersons()).thenReturn(List.of(personDto));

        ResponseEntity<List<PersonDto>> response = personController.findAllPersons();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());

        verify(personBusiness, times(1)).findAllPersons();
    }

    @Test
    void findPersonByDniTest() {
        PersonDto personDto = createPersonDto();

        when(personBusiness.findPersonByDni(DNI)).thenReturn(Optional.of(personDto));

        ResponseEntity<PersonDto> response = personController.findPersonByDni(DNI);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        verify(personBusiness, times(1)).findPersonByDni(DNI);
    }

    @Test
    void findPersonByDniNotFoundTest() {
        when(personBusiness.findPersonByDni(DNI)).thenReturn(Optional.empty());

        ResponseEntity<PersonDto> response = personController.findPersonByDni(DNI);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(personBusiness, times(1)).findPersonByDni(DNI);
    }

    @Test
    void createPersonTest() {
        CreatePersonRequestDto requestDto = createCreatePersonRequestDto();
        PersonDto personDto = createPersonDto();

        when(personBusiness.createPerson(requestDto)).thenReturn(personDto);

        ResponseEntity<PersonDto> response = personController.createPerson(requestDto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());

        verify(personBusiness, times(1)).createPerson(requestDto);
    }

    @Test
    void updatePersonByDniTest() {
        UpdatePersonRequestDto requestDto = createUpdatePersonRequestDto();
        PersonDto personDto = createUpdatedPersonDto();

        when(personBusiness.updatePersonByDni(DNI, requestDto)).thenReturn(Optional.of(personDto));

        ResponseEntity<PersonDto> response = personController.updatePersonByDni(DNI, requestDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        verify(personBusiness, times(1)).updatePersonByDni(DNI, requestDto);
    }

    @Test
    void updatePersonByDniNotFoundTest() {
        UpdatePersonRequestDto requestDto = createUpdatePersonRequestDto();

        when(personBusiness.updatePersonByDni(DNI, requestDto)).thenReturn(Optional.empty());

        ResponseEntity<PersonDto> response = personController.updatePersonByDni(DNI, requestDto);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(personBusiness, times(1)).updatePersonByDni(DNI, requestDto);
    }

    @Test
    void deletePersonByDniTest() {
        when(personBusiness.deletePersonByDni(DNI)).thenReturn(true);

        ResponseEntity<Void> response = personController.deletePersonByDni(DNI);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());

        verify(personBusiness, times(1)).deletePersonByDni(DNI);
    }

    @Test
    void deletePersonByDniNotFoundTest() {
        when(personBusiness.deletePersonByDni(DNI)).thenReturn(false);

        ResponseEntity<Void> response = personController.deletePersonByDni(DNI);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(personBusiness, times(1)).deletePersonByDni(DNI);
    }

    @Test
    void deleteAllPersonsTest() {
        doNothing().when(personBusiness).deleteAllPersons();

        ResponseEntity<Void> response = personController.deleteAllPersons();

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());

        verify(personBusiness, times(1)).deleteAllPersons();
    }

    private PersonDto createPersonDto() {
        ContactDetailsDto contactDetailsDto = new ContactDetailsDto();
        contactDetailsDto.setTelephone(600111222);
        contactDetailsDto.setStreet("Calle Mayor 1");
        contactDetailsDto.setEmail("miguel@test.com");

        PersonDto personDto = new PersonDto();
        personDto.setName("Miguel");
        personDto.setLastName("Lopez");
        personDto.setDni(DNI);
        personDto.setContactDetails(contactDetailsDto);

        return personDto;
    }

    private PersonDto createUpdatedPersonDto() {
        ContactDetailsDto contactDetailsDto = new ContactDetailsDto();
        contactDetailsDto.setTelephone(699888777);
        contactDetailsDto.setStreet("Avenida Nueva 10");
        contactDetailsDto.setEmail("juan@test.com");

        PersonDto personDto = new PersonDto();
        personDto.setName("Juan");
        personDto.setLastName("Garcia");
        personDto.setDni(DNI);
        personDto.setContactDetails(contactDetailsDto);

        return personDto;
    }

    private CreatePersonRequestDto createCreatePersonRequestDto() {
        ContactDetailsDto contactDetailsDto = new ContactDetailsDto();
        contactDetailsDto.setTelephone(600111222);
        contactDetailsDto.setStreet("Calle Mayor 1");
        contactDetailsDto.setEmail("miguel@test.com");

        CreatePersonRequestDto requestDto = new CreatePersonRequestDto();
        requestDto.setName("Miguel");
        requestDto.setLastName("Lopez");
        requestDto.setDni(DNI);
        requestDto.setContactDetails(contactDetailsDto);

        return requestDto;
    }

    private UpdatePersonRequestDto createUpdatePersonRequestDto() {
        ContactDetailsDto contactDetailsDto = new ContactDetailsDto();
        contactDetailsDto.setTelephone(699888777);
        contactDetailsDto.setStreet("Avenida Nueva 10");
        contactDetailsDto.setEmail("juan@test.com");

        UpdatePersonRequestDto requestDto = new UpdatePersonRequestDto();
        requestDto.setName("Juan");
        requestDto.setLastName("Garcia");
        requestDto.setContactDetails(contactDetailsDto);

        return requestDto;
    }

}
