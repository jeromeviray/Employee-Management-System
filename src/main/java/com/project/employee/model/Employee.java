package com.project.employee.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 2, message = "Should not be less than 2 character.")
    @NotNull(message = "Should not be empty or null")
    @Column(name = "firstname")
    private String firstName;

    @Column(name = "middlename")
    private String middleName;

    @Size(min = 2, message = "Should not be less than 2 character.")
    @NotNull(message = "Should not be empty or null")
    @Column(name = "lastname")
    private String lastName;

    @Pattern( regexp = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$",
                message = "Invalid Contact Format")
    @Column(name = "contact")
    private String contact;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "birthday")
    @Past( message = "Your birthday should not be in the future" )
    private  Date birthday;

    @Column(name = "address")
    private String address;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    public Employee() {
    }

    public Employee( long id,
                     String firstName,
                     String middleName,
                     String lastName,
                     String contact,
                     @Past( message = "Your birthday should not be in the future" ) Date birthday,
                     String address ) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.contact = contact;
        this.birthday = birthday;
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName( String firstName ) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName( String middleName ) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName( String lastName ) {
        this.lastName = lastName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact( String contact ) {
        this.contact = contact;
    }

    public @Past( message = "Your birthday should not be in the future" ) Date getBirthday() {
        return birthday;
    }

    public void setBirthday( @Past( message = "Your birthday should not be in the future" ) Date birthday ) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress( String address ) {
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", contact='" + contact + '\'' +
                ", birthday=" + birthday +
                ", address='" + address + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
