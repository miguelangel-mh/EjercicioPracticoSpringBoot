package com.ejercicio.ejercicioPracticas.persistence.repository;

import com.ejercicio.ejercicioPracticas.persistence.model.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPersonRepository extends JpaRepository<PersonEntity, Long> {

    Optional<PersonEntity> findByDni(String dni) ;
    boolean existsByDni(String dni) ;
    void deleteByDni(String dni) ;

}
