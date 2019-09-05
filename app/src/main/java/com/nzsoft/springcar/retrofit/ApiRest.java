package com.nzsoft.springcar.retrofit;

import com.nzsoft.springcar.model.Car;
import com.nzsoft.springcar.model.Client;
import com.nzsoft.springcar.model.Office;
import com.nzsoft.springcar.model.Reservation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiRest {

    // CLIENTS
    @GET ("clients")
    public Call<Client> getClientById (@Query("id") Long id);

    // CARS
    @GET ("cars")
    public Call<List<Car>> getAllCars();

    @GET ("cars/nodisponibles")
    public Call<List<Car>> getNotAvailableCars(@Query("fin") String fechaFin, @Query("inicio") String fechaInicio, @Query("officeId") Long officeId);


    // OFFICES
    @GET ("offices")
    public Call<List<Office>> getAllOffices();

    // RESERVATION
    @POST ("reservations")
    public Call<Reservation> createReservation(@Body Reservation reservation);

}
