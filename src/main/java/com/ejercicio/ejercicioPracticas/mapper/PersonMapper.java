package com.ejercicio.ejercicioPracticas.mapper;

import com.ejercicio.ejercicioPracticas.dto.ContactDetailsDto;
import com.ejercicio.ejercicioPracticas.dto.PersonDto;
import com.ejercicio.ejercicioPracticas.persistence.model.ContactEntity;
import com.ejercicio.ejercicioPracticas.persistence.model.PersonEntity;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

public final class PersonMapper {

    private PersonMapper() {
        super();
    }

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

    private static String buildFullName(final PersonEntity personEntity) {
        return StringUtils.trim(
                personEntity.getName() + " " + personEntity.getLastName()
        );
    }

    private static ContactDetailsDto buildContactDetails(final ContactEntity contactEntity) {
        return ContactDetailsDto.builder()
                .telephone(contactEntity.getTelephone())
                .street(contactEntity.getStreet())
                .email(contactEntity.getEmail())
                .build();
    }
}