package com.ejercicio.ejercicioPracticas.business;

import com.ejercicio.ejercicioPracticas.dto.ContactDetailsDto;
import com.ejercicio.ejercicioPracticas.dto.CreatePersonRequestDto;
import com.ejercicio.ejercicioPracticas.dto.PersonDto;
import com.ejercicio.ejercicioPracticas.dto.UpdatePersonRequestDto;
import com.ejercicio.ejercicioPracticas.exception.BusinessException;
import com.ejercicio.ejercicioPracticas.persistence.id.ContactId;
import com.ejercicio.ejercicioPracticas.persistence.model.ContactEntity;
import com.ejercicio.ejercicioPracticas.persistence.model.PersonEntity;
import com.ejercicio.ejercicioPracticas.persistence.repository.IContactRepository;
import com.ejercicio.ejercicioPracticas.persistence.repository.IPersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonBusinessImplTest {

    private static final String DNI = "12345678A";

    @Mock
    private IPersonRepository personRepository;

    @Mock
    private IContactRepository contactRepository;

    @InjectMocks
    private PersonBusinessImpl personBusiness;

    @Test
    void findAllPersonsTest() {
        PersonEntity personEntity = createPersonEntity(1L, "Miguel", "Lopez", DNI);
        ContactEntity contactEntity = createContactEntity(personEntity, 600111222, "Calle Mayor 1", "miguel@test.com");

        when(personRepository.findAll()).thenReturn(List.of(personEntity));
        when(contactRepository.findByContactIdDni(DNI)).thenReturn(Optional.of(contactEntity));

        List<PersonDto> result = personBusiness.findAllPersons();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(personRepository, times(1)).findAll();
        verify(contactRepository, times(1)).findByContactIdDni(DNI);
    }

    @Test
    void findAllPersonsEmptyTest() {
        when(personRepository.findAll()).thenReturn(List.of());

        List<PersonDto> result = personBusiness.findAllPersons();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(personRepository, times(1)).findAll();
        verify(contactRepository, never()).findByContactIdDni(any());
    }

    @Test
    void findPersonByDniTest() {
        PersonEntity personEntity = createPersonEntity(1L, "Miguel", "Lopez", DNI);
        ContactEntity contactEntity = createContactEntity(personEntity, 600111222, "Calle Mayor 1", "miguel@test.com");

        when(personRepository.findByDni(DNI)).thenReturn(Optional.of(personEntity));
        when(contactRepository.findByContactIdDni(DNI)).thenReturn(Optional.of(contactEntity));

        Optional<PersonDto> result = personBusiness.findPersonByDni(DNI);

        assertTrue(result.isPresent());
        assertNotNull(result.get());
        verify(personRepository, times(1)).findByDni(DNI);
        verify(contactRepository, times(1)).findByContactIdDni(DNI);
    }

    @Test
    void findPersonByDniNotFoundTest() {
        when(personRepository.findByDni(DNI)).thenReturn(Optional.empty());

        Optional<PersonDto> result = personBusiness.findPersonByDni(DNI);

        assertTrue(result.isEmpty());
        verify(personRepository, times(1)).findByDni(DNI);
        verify(contactRepository, never()).findByContactIdDni(any());
    }

    @Test
    void findPersonByDniWithoutContactTest() {
        PersonEntity personEntity = createPersonEntity(1L, "Miguel", "Lopez", DNI);

        when(personRepository.findByDni(DNI)).thenReturn(Optional.of(personEntity));
        when(contactRepository.findByContactIdDni(DNI)).thenReturn(Optional.empty());

        Optional<PersonDto> result = personBusiness.findPersonByDni(DNI);

        assertTrue(result.isEmpty());
        verify(personRepository, times(1)).findByDni(DNI);
        verify(contactRepository, times(1)).findByContactIdDni(DNI);
    }

    @Test
    void createPersonTest() {
        CreatePersonRequestDto requestDto = createCreatePersonRequestDto();

        PersonEntity savedPerson = createPersonEntity(1L, "Miguel", "Lopez", DNI);
        ContactEntity savedContact = createContactEntity(savedPerson, 600111222, "Calle Mayor 1", "miguel@test.com");

        when(personRepository.existsByDni(DNI)).thenReturn(false);
        when(personRepository.save(any(PersonEntity.class))).thenReturn(savedPerson);
        when(contactRepository.save(any(ContactEntity.class))).thenReturn(savedContact);

        PersonDto result = personBusiness.createPerson(requestDto);

        assertNotNull(result);
        verify(personRepository, times(1)).existsByDni(DNI);
        verify(personRepository, times(1)).save(any(PersonEntity.class));
        verify(contactRepository, times(1)).save(any(ContactEntity.class));
    }

    @Test
    void createPersonDuplicatedDniTest() {
        CreatePersonRequestDto requestDto = createCreatePersonRequestDto();

        when(personRepository.existsByDni(DNI)).thenReturn(true);

        assertThrows(BusinessException.class, () -> personBusiness.createPerson(requestDto));

        verify(personRepository, times(1)).existsByDni(DNI);
        verify(personRepository, never()).save(any(PersonEntity.class));
        verify(contactRepository, never()).save(any(ContactEntity.class));
    }

    @Test
    void updatePersonByDniTest() {
        UpdatePersonRequestDto requestDto = createUpdatePersonRequestDto();

        PersonEntity personEntityDb = createPersonEntity(1L, "Miguel", "Lopez", DNI);
        ContactEntity contactEntityDb = createContactEntity(personEntityDb, 600111222, "Calle Mayor 1", "miguel@test.com");

        ContactEntity updatedContact = createContactEntity(personEntityDb, 699888777, "Avenida Nueva 10", "miguelupdate@test.com");
        PersonEntity updatedPerson = createPersonEntity(1L, "Juan", "Garcia", DNI);

        when(personRepository.findByDni(DNI)).thenReturn(Optional.of(personEntityDb));
        when(contactRepository.findByContactIdDni(DNI)).thenReturn(Optional.of(contactEntityDb));
        when(personRepository.save(any(PersonEntity.class))).thenReturn(updatedPerson);
        when(contactRepository.save(any(ContactEntity.class))).thenReturn(updatedContact);

        Optional<PersonDto> result = personBusiness.updatePersonByDni(DNI, requestDto);

        assertTrue(result.isPresent());
        assertNotNull(result.get());
        verify(personRepository, times(1)).findByDni(DNI);
        verify(contactRepository, times(1)).findByContactIdDni(DNI);
        verify(personRepository, times(1)).save(any(PersonEntity.class));
        verify(contactRepository, times(1)).save(any(ContactEntity.class));
    }

    @Test
    void updatePersonByDniPersonNotFoundTest() {
        UpdatePersonRequestDto requestDto = createUpdatePersonRequestDto();

        when(personRepository.findByDni(DNI)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> personBusiness.updatePersonByDni(DNI, requestDto));

        verify(personRepository, times(1)).findByDni(DNI);
        verify(contactRepository, never()).findByContactIdDni(any());
        verify(personRepository, never()).save(any(PersonEntity.class));
        verify(contactRepository, never()).save(any(ContactEntity.class));
    }

    @Test
    void deletePersonByDniTest() {
        PersonEntity personEntity = createPersonEntity(1L, "Miguel", "Lopez", DNI);

        when(personRepository.findByDni(DNI)).thenReturn(Optional.of(personEntity));
        doNothing().when(contactRepository).deleteByContactIdDni(DNI);
        doNothing().when(personRepository).delete(personEntity);

        boolean result = personBusiness.deletePersonByDni(DNI);

        assertTrue(result);
        verify(personRepository, times(1)).findByDni(DNI);
        verify(contactRepository, times(1)).deleteByContactIdDni(DNI);
        verify(personRepository, times(1)).delete(personEntity);
    }

    @Test
    void deletePersonByDniNotFoundTest() {
        when(personRepository.findByDni(DNI)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> personBusiness.deletePersonByDni(DNI));

        verify(personRepository, times(1)).findByDni(DNI);
        verify(contactRepository, never()).deleteByContactIdDni(any());
        verify(personRepository, never()).delete(any(PersonEntity.class));
    }

    @Test
    void deleteAllPersonsTest() {
        doNothing().when(contactRepository).deleteAll();
        doNothing().when(personRepository).deleteAll();

        personBusiness.deleteAllPersons();

        verify(contactRepository, times(1)).deleteAll();
        verify(personRepository, times(1)).deleteAll();
    }

    private PersonEntity createPersonEntity(Long idPerson, String name, String lastName, String dni) {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setIdPerson(idPerson);
        personEntity.setName(name);
        personEntity.setLastName(lastName);
        personEntity.setDni(dni);
        return personEntity;
    }

    private ContactEntity createContactEntity(PersonEntity personEntity, int telephone, String street, String email) {
        ContactEntity contactEntity = new ContactEntity();
        contactEntity.setContactId(new ContactId(personEntity.getIdPerson(), personEntity.getDni()));
        contactEntity.setPerson(personEntity);
        contactEntity.setTelephone(telephone);
        contactEntity.setStreet(street);
        contactEntity.setEmail(email);
        return contactEntity;
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
        contactDetailsDto.setEmail("miguelupdate@test.com");

        UpdatePersonRequestDto requestDto = new UpdatePersonRequestDto();
        requestDto.setName("Juan");
        requestDto.setLastName("Garcia");
        requestDto.setContactDetails(contactDetailsDto);

        return requestDto;
    }

}
