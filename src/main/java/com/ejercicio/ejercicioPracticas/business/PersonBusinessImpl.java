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

/**
 * Implementación de la lógica de negocio encargada de gestionar las operaciones
 * relacionadas con personas y sus datos de contacto.
 */
@Service
public class PersonBusinessImpl implements PersonBusiness{

    private final IPersonRepository personRepository ;
    private final IContactRepository contactRepository ;

    /**
     * Constructor de la clase de negocio de personas.
     *
     * @param personRepository repositorio encargado de las operaciones sobre personas.
     * @param contactRepository repositorio encargado de las operaciones sobre contactos.
     */
    public PersonBusinessImpl(IPersonRepository personRepository, IContactRepository contactRepository){
        this.personRepository = personRepository ;
        this.contactRepository = contactRepository ;
    }

    /**
     * Obtiene todas las personas registradas junto con sus datos de contacto.
     *
     * @return lista de personas en formato DTO.
     */
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

    /**
     * Busca una persona a partir de su DNI y devuelve sus datos junto con el contacto asociado.
     *
     * @param dni DNI de la persona a buscar.
     * @return un {@link Optional} con la persona encontrada, o vacío si no existe
     * o no dispone de contacto asociado.
     */
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

    /**
     * Crea una nueva persona y sus datos de contacto asociados.
     *
     * @param requestDto datos necesarios para crear la persona.
     * @return la persona creada en formato DTO.
     * @throws BusinessException si el DNI ya existe o si se produce un error durante el mapeo.
     */
    @Override
    @Transactional
    public PersonDto createPerson(CreatePersonRequestDto requestDto) {
        PersonValidator.validateDuplicatedDni(personRepository.existsByDni(requestDto.getDni()));

        PersonEntity savedPerson = saveNewPerson(requestDto);
        ContactEntity savedContact = saveNewContact(savedPerson, requestDto.getContactDetails());

        return PersonMapper.toPersonDto(savedPerson, savedContact)
                .orElseThrow(() -> new BusinessException("Error creando a la persona"));
    }

    /**
     * Actualiza los datos de una persona existente y de su contacto asociado a partir del DNI.
     *
     * @param dni DNI de la persona a actualizar.
     * @param requestDto nuevos datos de la persona.
     * @return un {@link Optional} con la persona actualizada en formato DTO.
     * @throws BusinessException si la persona o el contacto no existen.
     */
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

    /**
     * Elimina una persona y su contacto asociado a partir del DNI.
     *
     * @param dni DNI de la persona a eliminar.
     * @return {@code true} si la eliminación se realiza correctamente.
     * @throws BusinessException si la persona no existe.
     */
    @Override
    @Transactional
    public boolean deletePersonByDni(String dni) {
        Optional<PersonEntity> personEntityOptional = personRepository.findByDni(dni) ;
        PersonValidator.validateExistingPerson(personEntityOptional);

        contactRepository.deleteByContactIdDni(dni);
        personRepository.delete(personEntityOptional.orElseThrow());

        return true;
    }

    /**
     * Elimina todas las personas registradas y todos sus contactos.
     */
    @Override
    @Transactional
    public void deleteAllPersons() {
        contactRepository.deleteAll();
        personRepository.deleteAll();
    }


    // Métodos auxiliares

    /**
     * Crea y guarda una nueva entidad de persona a partir de los datos recibidos.
     *
     * @param requestDto datos de la persona a crear.
     * @return la entidad persona guardada.
     */
    private PersonEntity saveNewPerson(final CreatePersonRequestDto requestDto) {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setName(requestDto.getName());
        personEntity.setLastName(requestDto.getLastName());
        personEntity.setDni(requestDto.getDni());

        return personRepository.save(personEntity);
    }

    /**
     * Crea y guarda una nueva entidad de contacto asociada a una persona.
     *
     * @param savedPerson persona previamente guardada.
     * @param contactDetailsDto datos de contacto a guardar.
     * @return la entidad contacto guardada.
     */
    private ContactEntity saveNewContact(final PersonEntity savedPerson, final ContactDetailsDto contactDetailsDto) {

        ContactEntity contactEntity = new ContactEntity();
        contactEntity.setContactId(new ContactId(savedPerson.getIdPerson(), savedPerson.getDni()));
        contactEntity.setPerson(savedPerson);
        contactEntity.setTelephone(contactDetailsDto.getTelephone());
        contactEntity.setStreet(contactDetailsDto.getStreet());
        contactEntity.setEmail(contactDetailsDto.getEmail());

        return contactRepository.save(contactEntity);
    }

    /**
     * Actualiza los datos básicos de una persona existente.
     *
     * @param personEntityDb entidad persona recuperada de base de datos.
     * @param requestDto nuevos datos personales.
     * @return la entidad persona actualizada y guardada.
     */
    private PersonEntity updatePersonEntity(final PersonEntity personEntityDb, final UpdatePersonRequestDto requestDto) {

        personEntityDb.setName(requestDto.getName());
        personEntityDb.setLastName(requestDto.getLastName());

        return personRepository.save(personEntityDb);
    }

    /**
     * Actualiza los datos de contacto de una persona existente.
     *
     * @param contactEntityDb entidad contacto recuperada de base de datos.
     * @param contactDetailsDto nuevos datos de contacto.
     * @return la entidad contacto actualizada y guardada.
     */
    private ContactEntity updateContactEntity(final ContactEntity contactEntityDb, final ContactDetailsDto contactDetailsDto) {

        contactEntityDb.setTelephone(contactDetailsDto.getTelephone());
        contactEntityDb.setStreet(contactDetailsDto.getStreet());
        contactEntityDb.setEmail(contactDetailsDto.getEmail());

        return contactRepository.save(contactEntityDb);
    }

}
