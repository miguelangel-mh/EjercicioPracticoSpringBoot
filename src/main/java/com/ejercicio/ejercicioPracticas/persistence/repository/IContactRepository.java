package com.ejercicio.ejercicioPracticas.persistence.repository;

import com.ejercicio.ejercicioPracticas.persistence.id.ContactId;
import com.ejercicio.ejercicioPracticas.persistence.model.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IContactRepository extends JpaRepository<ContactEntity, ContactId> {

    Optional<ContactEntity> findByContactIdDni(String dni) ;
    boolean existsByContactIdDni(String dni) ;
    void deleteByContactIdDni(String dni) ;

}
