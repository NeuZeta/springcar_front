package com.nzsoft.springcar.model;

import java.util.Date;
import java.util.List;

public class Reservation {

    private Long id;
    private Date reservationDate;
    private Date pickUpDate;
    private Date dropOffDate;
    private Client client;
    private Car car;
    private InsuranceType insuranceType;
    private boolean hasTireAndGlassProtection;
    private List<CommonExtra> commonExtras;
    private Double totalPrice;

    public Reservation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Date getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(Date pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public Date getDropOffDate() {
        return dropOffDate;
    }

    public void setDropOffDate(Date dropOffDate) {
        this.dropOffDate = dropOffDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public InsuranceType getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(InsuranceType insuranceType) {
        this.insuranceType = insuranceType;
    }

    public boolean isHasTireAndGlassProtection() {
        return hasTireAndGlassProtection;
    }

    public void setHasTireAndGlassProtection(boolean hasTireAndGlassProtection) {
        this.hasTireAndGlassProtection = hasTireAndGlassProtection;
    }

    public List<CommonExtra> getCommonExtras() {
        return commonExtras;
    }

    public void setCommonExtras(List<CommonExtra> commonExtras) {
        this.commonExtras = commonExtras;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", reservationDate=" + reservationDate +
                ", pickUpDate=" + pickUpDate +
                ", dropOffDate=" + dropOffDate +
                ", client=" + client +
                ", car=" + car +
                ", insuranceType=" + insuranceType +
                ", commonExtras=" + commonExtras +
                ", totalPrice=" + totalPrice +
                '}';
    }

    /*
    *
    * INNER ENUM InsuranceType
    *
    * */
    public enum InsuranceType{
        BASE, TOP;
    }
}
