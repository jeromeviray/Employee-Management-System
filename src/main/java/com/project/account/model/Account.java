package com.project.account.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "account", uniqueConstraints = {
@UniqueConstraint(columnNames = "username") })
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min=5, message = "Username should have at least more than 5 character.")
    @Column(name = "username")
    private String username;

    @Size(min=8, message = "Password should have at least more than 8 character.")
    @Column(name = "password")
    private String password;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "role")
    private AccountRole role;

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername( String username ) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword( String password ) {
        this.password = password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt( Date createdAt ) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public AccountRole getRole() {
        return role;
    }

    public void setRole( AccountRole role ) {
        this.role = role;
    }

    @Override
    public boolean equals( Object o ) {
        if( this == o ) return true;
        if( ! ( o instanceof Account ) ) return false;
        Account account = ( Account ) o;
        return getId() == account.getId() && Objects.equals( getUsername(), account.getUsername() ) && Objects.equals( getPassword(), account.getPassword() ) && Objects.equals( getCreatedAt(), account.getCreatedAt() ) && Objects.equals( getUpdatedAt(), account.getUpdatedAt() ) && Objects.equals( getRole(), account.getRole() );
    }

    @Override
    public int hashCode() {
        return Objects.hash( getId(), getUsername(), getPassword(), getCreatedAt(), getUpdatedAt(), getRole() );
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", accountRole=" + role.getRoleName() +
                '}';
    }
}
