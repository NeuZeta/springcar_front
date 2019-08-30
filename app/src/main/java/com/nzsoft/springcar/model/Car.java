package com.nzsoft.springcar.model;

public class Car {

    private Long id;
    private String model;
    private Transmission transmission;
    private Office office;
    private Category category;
    private boolean hasAirConditioner;
    private int numberOfSeats;
    private int numberOfDoors;
    private int suitcasesCapacity;
    private String photo;
    private double basePrice;

    public Car() {
    }

    public Car(String model, Transmission transmission, Office office, boolean hasAirConditioner, int numberOfSeats, int numberOfDoors, int suitcasesCapacity, String photo, double basePrice) {
        this.model = model;
        this.transmission = transmission;
        this.office = office;
        this.hasAirConditioner = hasAirConditioner;
        this.numberOfSeats = numberOfSeats;
        this.numberOfDoors = numberOfDoors;
        this.suitcasesCapacity = suitcasesCapacity;
        this.photo = photo;
        this.basePrice = basePrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isHasAirConditioner() {
        return hasAirConditioner;
    }

    public void setHasAirConditioner(boolean hasAirConditioner) {
        this.hasAirConditioner = hasAirConditioner;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public int getNumberOfDoors() {
        return numberOfDoors;
    }

    public void setNumberOfDoors(int numberOfDoors) {
        this.numberOfDoors = numberOfDoors;
    }

    public int getSuitcasesCapacity() {
        return suitcasesCapacity;
    }

    public void setSuitcasesCapacity(int suitcasesCapacity) {
        this.suitcasesCapacity = suitcasesCapacity;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", transmission=" + transmission +
                ", office=" + office +
                ", hasAirConditioner=" + hasAirConditioner +
                ", numberOfSeats=" + numberOfSeats +
                ", numberOfDoors=" + numberOfDoors +
                ", suitcasesCapacity=" + suitcasesCapacity +
                ", photo='" + photo + '\'' +
                '}';
    }

    /*
     *
     * INNER ENUM InsuranceType
     *
     * */

    enum Transmission {

        AUTO, MANUAL;
    }
}
