package com.nzsoft.springcar.model;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Reservation {

    private Long id;

    private Date reservationDate;
    private Date pickupDate;
    private Date dropOffDate;

    private Client client;
    private Car car;
    private InsuranceType insuranceType;
    private boolean hasTireAndGlassProtection;
    private List<CommonExtra> commonExtras;
    private Double price;

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
        return pickupDate;
    }

    public void setPickUpDate(Date pickUpDate) {
        this.pickupDate = pickUpDate;
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

    public Double getPrice () {
        return this.price;
    }

    public void setPrice (){
        this.price = getTotalPrice();
    }

    private Double getTotalPrice() {

        double price = 0;

        //Primero calculamos los días de reserva

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = sdf.format(this.pickupDate);
        String endDate = sdf.format(this.dropOffDate);

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        int differenceInDays = Days.daysBetween(start, end).getDays();

        if (this.getCar() != null) {
            //Multiplicamos los días por el precio base del coche
            price = differenceInDays * this.getCar().getBasePrice();

            //Le sumamos el precio del seguro por categoria

            switch (insuranceType) {
                case BASE:
                    price += this.getCar().getCategory().getBaseInsurancePrice();
                    break;
                case TOP:
                    price += this.getCar().getCategory().getTopInsurancePrice();
                    break;
            }

            //Si tiene proteccion de ruedas y cristales le sumamos el precio por categoria

            if (hasTireAndGlassProtection) {
                price += this.getCar().getCategory().getTireAndGlassProtectionPrice();
            }
        }

        //Sumamos el precio de cada extra que se le ha añadido

        if (!commonExtras.isEmpty()) {
            for (CommonExtra commonExtra : commonExtras) {
                price += commonExtra.getPrice();
            }
        }

        return price;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", reservationDate=" + reservationDate +
                ", pickupDate=" + pickupDate +
                ", dropOffDate=" + dropOffDate +
                ", client=" + client +
                ", car=" + car +
                ", insuranceType=" + insuranceType +
                ", commonExtras=" + commonExtras +
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
