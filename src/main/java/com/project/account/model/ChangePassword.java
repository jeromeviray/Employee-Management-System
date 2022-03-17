package com.project.account.model;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ChangePassword {
    @NotNull(message = "Id cannot be empty")
    private int id;
    @Size(min=8, message = "Password should have at least more than 8 character.")
    private String password;
    private String confirmPassword;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword( String password ) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword( String confirmPassword ) {
        this.confirmPassword = confirmPassword;
    }


}
