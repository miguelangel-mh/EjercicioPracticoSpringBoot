package com.ejercicio.ejercicioPracticas.persistence.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
public class ContactId implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "ID_PERSON", nullable = false)
    private Long idPerson ;

    @Column(name = "DNI", nullable = false, length = 9)
    private String dni ;

    public ContactId(){
        super();
    }

    public ContactId(final Long idPerson, final String dni) {
        this.idPerson = idPerson;
        this.dni = dni;
    }

    public Long getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(final Long idPerson) {
        this.idPerson = idPerson;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(final String dni) {
        this.dni = dni;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ContactId{");
        builder.append("idPerson=").append(idPerson);
        builder.append(", dni='").append(dni).append('\'');
        builder.append('}');
        return builder.toString();
    }

}
