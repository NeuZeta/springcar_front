package com.nzsoft.springcar.retrofit;

import com.nzsoft.springcar.model.Car;
import com.nzsoft.springcar.model.Office;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiRest {

    // CARS
    @GET ("cars")
    public Call<List<Car>> getAllCars();


    // OFFICES
    @GET ("offices")
    public Call<List<Office>> getAllOffices();

}
