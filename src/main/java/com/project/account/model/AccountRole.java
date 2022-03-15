package com.project.account.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "account_role")
@JsonIgnoreProperties({"id"})
public class AccountRole implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "role_name")
    private String roleName;

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName( String roleName ) {
        this.roleName = roleName;
    }

    @Override
    public boolean equals( Object o ) {
        if( this == o ) return true;
        if( ! ( o instanceof AccountRole ) ) return false;
        AccountRole that = ( AccountRole ) o;
        return getId() == that.getId() && Objects.equals( getRoleName(), that.getRoleName() );
    }

    @Override
    public int hashCode() {
        return Objects.hash( getId(), getRoleName() );
    }

    @Override
    public String toString() {
        return "AccountRole{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
