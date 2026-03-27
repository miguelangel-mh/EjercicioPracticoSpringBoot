package com.ejercicio.ejercicioPracticas.business;

import com.ejercicio.ejercicioPracticas.dto.ContactDetailsDto;
import com.ejercicio.ejercicioPracticas.dto.CreatePersonRequestDto;
import com.ejercicio.ejercicioPracticas.dto.PersonDto;
import com.ejercicio.ejercicioPracticas.dto.UpdatePersonRequestDto;
import com.ejercicio.ejercicioPracticas.exception.BusinessException;
import com.ejercicio.ejercicioPracticas.mapper.PersonMapper;
import com.ejercicio.ejercicioPracticas.persistence.id.ContactId;
import com.ejercicio.ejercicioPracticas.persistence.model.ContactEntity;
import com.ejercicio.ejercicioPracticas.persistence.model.PersonEntity;
import com.ejercicio.ejercicioPracticas.persistence.repository.IContactRepository;
import com.ejercicio.ejercicioPracticas.persistence.repository.IPersonRepository;
import com.ejercicio.ejercicioPracticas.validate.PersonValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonBusinessImpl implements PersonBusiness{

    private final IPersonRepository personRepository ;
    private final IContactRepository contactRepository ;

    public PersonBusinessImpl(IPersonRepository personRepository, IContactRepository contactRepository){
        this.personRepository = personRepository ;
        this.contactRepository = contactRepository ;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonDto> findAllPersons(){
        List<PersonEntity> personEntities = personRepository.findAll() ;
        List<PersonDto> personDtos = new ArrayList<>() ;

        for (PersonEntity personEntity : personEntities) {
            Optional<ContactEntity> contactEntityOptional = contactRepository.findByContactIdDni(personEntity.getDni()) ;

            if (contactEntityOptional.isPresent()){
                Optional<PersonDto> personDtoOptional = PersonMapper.toPersonDto(personEntity, contactEntityOptional.orElseThrow()) ;

                personDtoOptional.ifPresent(personDtos::add) ;
            }
        }

        return personDtos ;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PersonDto> findPersonByDni(String dni) {

        Optional<PersonEntity> personEntityOptional = personRepository.findByDni(dni) ;
        if (personEntityOptional.isPresent()) {
            Optional<ContactEntity> contactEntityOptional =
                    contactRepository.findByContactIdDni(dni);

            if (contactEntityOptional.isPresent()) {
                return PersonMapper.toPersonDto(
                        personEntityOptional.orElseThrow(),
                        contactEntityOptional.orElseThrow()
                );
            }
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public PersonDto createPerson(CreatePersonRequestDto requestDto) {
        PersonValidator.validateDuplicatedDni(personRepository.existsByDni(requestDto.getDni()));

        PersonEntity savedPerson = saveNewPerson(requestDto);
        ContactEntity savedContact = saveNewContact(savedPerson, requestDto.getContactDetails());

        return PersonMapper.toPersonDto(savedPerson, savedContact)
                .orElseThrow(() -> new BusinessException("Error creando a la persona"));
    }

    @Override
    @Transactional
    public Optional<PersonDto> updatePersonByDni(String dni, UpdatePersonRequestDto requestDto) {

        Optional<PersonEntity> personEntityOptional = personRepository.findByDni(dni);
        PersonValidator.validateExistingPerson(personEntityOptional);

        Optional<ContactEntity> contactEntityOptional = contactRepository.findByContactIdDni(dni);
        PersonValidator.validateExistingContact(contactEntityOptional);

        PersonEntity savedPerson = updatePersonEntity(
                personEntityOptional.orElseThrow(),
                requestDto
        );

        ContactEntity savedContact = updateContactEntity(
                contactEntityOptional.orElseThrow(),
                requestDto.getContactDetails()
        );

        return PersonMapper.toPersonDto(savedPerson, savedContact);
    }

    @Override
    @Transactional
    public boolean deletePersonByDni(String dni) {
        Optional<PersonEntity> personEntityOptional = personRepository.findByDni(dni) ;
        PersonValidator.validateExistingPerson(personEntityOptional);

        contactRepository.deleteByContactIdDni(dni);
        personRepository.delete(personEntityOptional.orElseThrow());

        return true;
    }

    @Override
    @Transactional
    public void deleteAllPersons() {
        contactRepository.deleteAll();
        personRepository.deleteAll();
    }


    // Métodos auxiliares

    private PersonEntity saveNewPerson(final CreatePersonRequestDto requestDto) {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setName(requestDto.getName());
        personEntity.setLastName(requestDto.getLastName());
        personEntity.setDni(requestDto.getDni());

        return personRepository.save(personEntity);
    }

    private ContactEntity saveNewContact(final PersonEntity savedPerson, final ContactDetailsDto contactDetailsDto) {

        ContactEntity contactEntity = new ContactEntity();
        contactEntity.setContactId(new ContactId(savedPerson.getIdPerson(), savedPerson.getDni()));
        contactEntity.setPerson(savedPerson);
        contactEntity.setTelephone(contactDetailsDto.getTelephone());
        contactEntity.setStreet(contactDetailsDto.getStreet());
        contactEntity.setEmail(contactDetailsDto.getEmail());

        return contactRepository.save(contactEntity);
    }

    private PersonEntity updatePersonEntity(final PersonEntity personEntityDb, final UpdatePersonRequestDto requestDto) {

        personEntityDb.setName(requestDto.getName());
        personEntityDb.setLastName(requestDto.getLastName());

        return personRepository.save(personEntityDb);
    }

    private ContactEntity updateContactEntity(final ContactEntity contactEntityDb, final ContactDetailsDto contactDetailsDto) {

        contactEntityDb.setTelephone(contactDetailsDto.getTelephone());
        contactEntityDb.setStreet(contactDetailsDto.getStreet());
        contactEntityDb.setEmail(contactDetailsDto.getEmail());

        return contactRepository.save(contactEntityDb);
    }

}
