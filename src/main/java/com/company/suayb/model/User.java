package com.company.suayb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Document(collection = "user")
public class User {

    @Id
    private String userId;

    @NotNull(message = "Username can not be null")
    @Size(min = 2, message = "Username must not be less than two characters")
    private String userName;

    @NotNull(message = "Name can not be null")
    @Size(min = 2, message = "Name must not be less than two characters")
    private String name;
    @NotNull(message = "Surname can not be null")
    @Size(min = 2, message = "Surname must not be less than two characters")
    private String surname;

    @NotNull(message = "PhoneNumber can not be null")
    @Size(min = 8, message = "PhoneNumber must not be less than eight characters")
    @NumberFormat
    private String phoneNumber;

    private String password;

    private int role;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
