package com.ejercicio.ejercicioPracticas.mapper;

import com.ejercicio.ejercicioPracticas.dto.ContactDetailsDto;
import com.ejercicio.ejercicioPracticas.dto.PersonDto;
import com.ejercicio.ejercicioPracticas.persistence.model.ContactEntity;
import com.ejercicio.ejercicioPracticas.persistence.model.PersonEntity;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

/**
 * Clase utilitaria encargada de transformar entidades de persona y contacto
 * en objetos DTO utilizados por la aplicación.
 */
public final class PersonMapper {

    /**
     * Constructor privado para evitar la instanciación de la clase utilitaria.
     */
    private PersonMapper() {
        super();
    }

    /**
     * Convierte una entidad de persona y una entidad de contacto en un objeto {@link PersonDto}.
     *
     * @param personEntity entidad que contiene los datos personales.
     * @param contactEntity entidad que contiene los datos de contacto.
     * @return un {@link Optional} con el DTO construido, o vacío si alguna de las entidades es nula.
     */
    public static Optional<PersonDto> toPersonDto(
            final PersonEntity personEntity,
            final ContactEntity contactEntity) {

        if (personEntity == null || contactEntity == null) {
            return Optional.empty();
        }

        return Optional.of(
                PersonDto.builder()
                        .idPerson(personEntity.getIdPerson())
                        .name(personEntity.getName())
                        .lastName(personEntity.getLastName())
                        .fullName(buildFullName(personEntity))
                        .dni(personEntity.getDni())
                        .contactDetails(buildContactDetails(contactEntity))
                        .build()
        );
    }

    /**
     * Construye el nombre completo de una persona a partir de su nombre y apellidos.
     *
     * @param personEntity entidad que contiene los datos personales.
     * @return nombre completo de la persona, eliminando espacios sobrantes.
     */
    private static String buildFullName(final PersonEntity personEntity) {
        return StringUtils.trim(
                personEntity.getName() + " " + personEntity.getLastName()
        );
    }

    /**
     * Construye un objeto {@link ContactDetailsDto} a partir de una entidad de contacto.
     *
     * @param contactEntity entidad que contiene los datos de contacto.
     * @return DTO con los datos de contacto.
     */
    private static ContactDetailsDto buildContactDetails(final ContactEntity contactEntity) {
        return ContactDetailsDto.builder()
                .telephone(contactEntity.getTelephone())
                .street(contactEntity.getStreet())
                .email(contactEntity.getEmail())
                .build();
    }
}