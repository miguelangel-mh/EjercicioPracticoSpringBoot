package com.ejercicio.ejercicioPracticas.persistence.repository;

import com.ejercicio.ejercicioPracticas.persistence.model.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio encargado de gestionar el acceso a datos de las personas.
 */
public interface IPersonRepository extends JpaRepository<PersonEntity, Long> {

    /**
     * Busca una persona a partir de su DNI.
     *
     * @param dni DNI de la persona a buscar.
     * @return un {@link Optional} con la persona encontrada, o vacío si no existe.
     */
    Optional<PersonEntity> findByDni(String dni) ;

    /**
     * Comprueba si existe una persona con un determinado DNI.
     *
     * @param dni DNI de la persona.
     * @return {@code true} si existe una persona con ese DNI, {@code false} en caso contrario.
     */
    boolean existsByDni(String dni) ;

    /**
     * Elimina una persona a partir de su DNI.
     *
     * @param dni DNI de la persona a eliminar.
     */
    void deleteByDni(String dni) ;

}
