package com.nzsoft.springcar.model;

public class Office {

    private Long id;
    private String name;
    private Location location;
    private Contact contact;

    public Office() {
    }

    public Office(String name, Location location, Contact contact) {
        this.name = name;
        this.location = location;
        this.contact = contact;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Office{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location=" + location +
                ", contact=" + contact +
                '}';
    }


}
