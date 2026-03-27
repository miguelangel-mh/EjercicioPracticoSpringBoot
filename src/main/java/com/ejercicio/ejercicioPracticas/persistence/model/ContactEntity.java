package com.ejercicio.ejercicioPracticas.persistence.model;

import com.ejercicio.ejercicioPracticas.persistence.id.ContactId;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "T_CONTACTS")
public class ContactEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ContactId contactId ;

    @MapsId("idPerson")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PERSON", referencedColumnName = "ID_PERSON", nullable = false)
    private PersonEntity person ;

    @NotNull(message = "El teléfono es Obligatorio")
    @Min(value = 100000000, message = "El teléfono debe tener 9 dígitos")
    @Max(value = 999999999, message = "El teléfono debe tener 9 dígitos")
    @Column(name = "TELEPHONE", nullable = false)
    private Integer telephone;

    @Column(name = "STREET")
    private String street;

    @Column(name = "EMAIL")
    private String email;

    public ContactEntity() {
        super();
    }

    public ContactId getContactId() {
        return contactId;
    }

    public void setContactId(final ContactId contactId) {
        this.contactId = contactId;
    }

    public PersonEntity getPerson() {
        return person;
    }

    public void setPerson(final PersonEntity person) {
        this.person = person;
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
        builder.append("ContactEntity{");
        builder.append("contactId=").append(contactId);
        builder.append(", telephone=").append(telephone);
        builder.append(", street='").append(street).append('\'');
        builder.append(", email='").append(email).append('\'');
        builder.append('}');
        return builder.toString();
    }



}
