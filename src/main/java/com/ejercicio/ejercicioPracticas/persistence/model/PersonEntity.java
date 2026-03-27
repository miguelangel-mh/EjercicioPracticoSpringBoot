package com.ejercicio.ejercicioPracticas.persistence.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "T_PERSONS")
public class PersonEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PERSON")
    private Long idPerson ;

    @NotBlank(message = "El nombre es Obligatorio")
    @Column(name = "NAME", nullable = false)
    private String name ;

    @NotBlank(message = "El Apellido es Obligatorio")
    @Column(name = "LASTNAME", nullable = false)
    private String lastname ;

    @NotBlank(message = "El DNI es Obligatorio")
    @Column(name = "DNI", nullable = false, length = 9)
    private String dni ;

    public PersonEntity(){
        super() ;
    }

    public Long getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(final Long idPerson) {
        this.idPerson = idPerson;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastName(final String lastName) {
        this.lastname = lastName;
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
        builder.append("PersonEntity{");
        builder.append("idPerson=").append(idPerson);
        builder.append(", name='").append(name).append('\'');
        builder.append(", lastName='").append(lastname).append('\'');
        builder.append(", dni='").append(dni).append('\'');
        builder.append('}');
        return builder.toString();
    }

}
