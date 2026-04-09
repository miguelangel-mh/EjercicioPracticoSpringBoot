package com.ejercicio.ejercicioPracticas.dto;

import com.ejercicio.ejercicioPracticas.validate.ValidDni;
import jakarta.validation.constraints.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * DTO que representa el formulario de entrada de datos de una persona
 */
public class PersonFormDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "El DNI es obligatorio")
    @ValidDni
    private String dni;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "Los apellidos son obligatorios")
    private String lastName;

    @NotNull(message = "El teléfono es Obligatorio")
    @Min(value = 100000000, message = "El teléfono debe tener 9 dígitos")
    @Max(value = 999999999, message = "El teléfono debe tener 9 dígitos")
    private Integer telephone;
    private String street;

    @Email(message = "El email no tiene un formato válido")
    private String email;

    public PersonFormDto() {
        super();
    }

    public String getDni() {
        return dni;
    }

    public void setDni(final String dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public Integer getTelephone() {
        return telephone;
    }

    public void setTelephone(final Integer telephone) {
        this.telephone = telephone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(final String street) {
        this.street = street;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PersonFormDto{");
        builder.append("dni='").append(dni).append('\'');
        builder.append(", name='").append(name).append('\'');
        builder.append(", lastName='").append(lastName).append('\'');
        builder.append(", telephone=").append(telephone);
        builder.append(", street='").append(street).append('\'');
        builder.append(", email='").append(email).append('\'');
        builder.append('}');
        return builder.toString();
    }

}
