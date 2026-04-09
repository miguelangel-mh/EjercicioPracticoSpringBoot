package com.ejercicio.ejercicioPracticas.business;

import com.ejercicio.ejercicioPracticas.dto.CreatePersonRequestDto;
import com.ejercicio.ejercicioPracticas.dto.PersonDto;
import com.ejercicio.ejercicioPracticas.dto.UpdatePersonRequestDto;

import java.util.List;
import java.util.Optional;

/**
 * Define las operaciones de negocio relacionadas con la gestión de personas.
 */
public interface PersonBusiness {

    /**
     * Obtiene todas las personas registradas.
     *
     * @return lista de personas en formato DTO.
     */
    List<PersonDto> findAllPersons() ;

    /**
     * Busca una persona a partir de su DNI.
     *
     * @param dni DNI de la persona a buscar.
     * @return un {@link Optional} con la persona encontrada, o vacío si no existe.
     */
    Optional<PersonDto> findPersonByDni(String dni) ;

    /**
     * Crea una nueva persona con sus datos personales y de contacto.
     *
     * @param requestDto datos necesarios para crear la persona.
     * @return la persona creada en formato DTO.
     */
    PersonDto createPerson(CreatePersonRequestDto requestDto) ;

    /**
     * Actualiza los datos de una persona existente a partir de su DNI.
     *
     * @param dni DNI de la persona a actualizar.
     * @param requestDto nuevos datos de la persona.
     * @return un {@link Optional} con la persona actualizada, o vacío si no se actualiza.
     */
    Optional<PersonDto> updatePersonByDni(String dni, UpdatePersonRequestDto requestDto) ;

    /**
     * Elimina una persona a partir de su DNI.
     *
     * @param dni DNI de la persona a eliminar.
     * @return {@code true} si la eliminación se realiza correctamente.
     */
    boolean deletePersonByDni(String dni) ;

    /**
     * Elimina todas las personas registradas.
     */
    void deleteAllPersons() ;

}
