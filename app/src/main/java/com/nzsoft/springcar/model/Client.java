package com.nzsoft.springcar.model;

import android.content.ContentValues;

public class Client {

    private Long id;
    private String firstName;
    private String lastName;
    private String idNumber;
    private Location location;
    private Contact contact;
    private String userName;
    private String password;

    public Client() {
    }

    public Client(String firstName, String lastName, String idNumber, Location location, Contact contact, String userName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.idNumber = idNumber;
        this.location = location;
        this.contact = contact;
        this.userName = userName;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", location=" + location +
                ", contact=" + contact +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
