package com.ejercicio.ejercicioPracticas.business;

import com.ejercicio.ejercicioPracticas.dto.CreatePersonRequestDto;
import com.ejercicio.ejercicioPracticas.dto.PersonDto;
import com.ejercicio.ejercicioPracticas.dto.UpdatePersonRequestDto;

import java.util.List;
import java.util.Optional;

public interface PersonBusiness {

    List<PersonDto> findAllPersons() ;
    Optional<PersonDto> findPersonByDni(String dni) ;
    PersonDto createPerson(CreatePersonRequestDto requestDto) ;
    Optional<PersonDto> updatePersonByDni(String dni, UpdatePersonRequestDto requestDto) ;
    boolean deletePersonByDni(String dni) ;
    void deleteAllPersons() ;

}
