package com.nzsoft.springcar.retrofit;

import com.nzsoft.springcar.model.Car;
import com.nzsoft.springcar.model.Client;
import com.nzsoft.springcar.model.Office;
import com.nzsoft.springcar.model.Reservation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiRest {

    // CLIENTS

    @GET ("clients/{id}")
    public Call<Client> getClientById (@Path("id") Long id);

    @POST ("clients")
    public Call<Client> createClient (@Body Client client);

    @PUT ("clients")
    public Call<Client> updateClient (@Body Client client);


    // CARS

    @GET ("cars")
    public Call<List<Car>> getAllCars();

    @GET ("cars/nodisponibles")
    public Call<List<Car>> getNotAvailableCars(@Query("fin") String fechaFin, @Query("inicio") String fechaInicio, @Query("officeId") Long officeId);


    // OFFICES

    @GET ("offices")
    public Call<List<Office>> getAllOffices();


    // RESERVATION

    @GET ("reservations")
    public Call<List<Reservation>> getAllReservations();

    @GET ("reservations/{id}")
    public Call <Reservation> getReservationById (@Path("id") Long id);

    @GET ("reservations/client/{id}")
    public Call <List<Reservation>> getReservationsByClient (@Path ("id") Long id);

    @POST ("reservations")
    public Call<Reservation> createReservation(@Body Reservation reservation);

    @DELETE ("reservations/{id}")
    public Call<Void> deleteReservationById (@Path("id") Long id);


}
