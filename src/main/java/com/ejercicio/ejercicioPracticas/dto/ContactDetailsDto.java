package com.ejercicio.ejercicioPracticas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;

/**
 * DTO que representa los datos de contacto asociados a una persona.
 */
@Schema(description = "Detalles del contacto de una persona")
public class ContactDetailsDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "Teléfono", example = "612345678")
    @NotNull(message = "El teléfono es Obligatorio")
    @Min(value = 100000000, message = "El teléfono debe tener 9 dígitos")
    @Max(value = 999999999, message = "El teléfono debe tener 9 dígitos")
    private Integer telephone;

    @Schema(description = "Dirección", example = "Calle Sol 12")
    private String street;

    @Schema(description = "Correo", example = "mmherrera@gmail.com")
    private String email;

    public ContactDetailsDto() {
        super();
    }

    private ContactDetailsDto(final Builder builder) {
        this.telephone = builder.telephone;
        this.street = builder.street;
        this.email = builder.email;
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Integer telephone;
        private String street;
        private String email;

        public Builder telephone(final Integer telephone) {
            this.telephone = telephone;
            return this;
        }

        public Builder street(final String street) {
            this.street = street;
            return this;
        }

        public Builder email(final String email) {
            this.email = email;
            return this;
        }

        public ContactDetailsDto build() {
            return new ContactDetailsDto(this);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ContactDetailsDto{");
        builder.append("telephone=").append(telephone);
        builder.append(", street='").append(street).append('\'');
        builder.append(", email='").append(email).append('\'');
        builder.append('}');
        return builder.toString();
    }
}
