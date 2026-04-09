package com.ejercicio.ejercicioPracticas.persistence.repository;

import com.ejercicio.ejercicioPracticas.persistence.id.ContactId;
import com.ejercicio.ejercicioPracticas.persistence.model.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio encargado de gestionar el acceso a datos de los contactos.
 */
public interface IContactRepository extends JpaRepository<ContactEntity, ContactId> {

    /**
     * Busca un contacto a partir del DNI asociado a su clave compuesta.
     *
     * @param dni DNI del contacto a buscar.
     * @return un {@link Optional} con el contacto encontrado, o vacío si no existe.
     */
    Optional<ContactEntity> findByContactIdDni(String dni) ;

    /**
     * Comprueba si existe un contacto asociado a un DNI.
     *
     * @param dni DNI del contacto.
     * @return {@code true} si existe un contacto con ese DNI, {@code false} en caso contrario.
     */
    boolean existsByContactIdDni(String dni) ;

    /**
     * Elimina un contacto a partir del DNI asociado a su clave compuesta.
     *
     * @param dni DNI del contacto a eliminar.
     */
    void deleteByContactIdDni(String dni) ;

}
