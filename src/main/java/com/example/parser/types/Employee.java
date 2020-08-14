package com.example.parser.types;

import com.example.parser.annotations.ParserType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Objects;

import java.util.List;

@ParserType(key = "employee-item")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee implements TypeInterface<Employee> {
    @JsonProperty("firstname")
    private String firstName;

    @JsonProperty("lastname")
    private String lastName;

    @JsonProperty
    private String title;

    @JsonProperty
    private String email;

    @JsonProperty
    private String telephone;

    public Employee() {}

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + lastName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equal(getFirstName(), employee.getFirstName()) &&
                Objects.equal(getLastName(), employee.getLastName()) &&
                Objects.equal(getTitle(), employee.getTitle()) &&
                Objects.equal(getEmail(), employee.getEmail()) &&
                Objects.equal(getTelephone(), employee.getTelephone());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getFirstName(), getLastName(), getTitle(), getEmail(), getTelephone());
    }

    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", title='" + title + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }

    @Override
    public TypeReference<List<Employee>> asTypeReference() {
        return new TypeReference<>() {};
    }
}
