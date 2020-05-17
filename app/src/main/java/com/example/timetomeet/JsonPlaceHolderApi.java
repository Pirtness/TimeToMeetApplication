package com.example.timetomeet;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi  {

    //login

    @GET("login")
    Call<Map<String, String>> login(@Query("username") String username, @Query("password") String password);

    @FormUrlEncoded
    @POST("login/reg")
    Call<Map<String, String>> register(@Field("username") String username, @Field("password") String password);


    //events

    @GET("event/{id}")
    Call<Event> getEvent(@Path("id") Long id);

    @GET("event/get_all")
    Call<List<Event>> getEvents();

    @GET("event/get_all/{username}")
    Call<List<Event>> getEventsOfUser(@Path("username") String username);

    @POST("event/create")
    Call<Event> createEvent(@Body Event event);

    @PUT("event/change/{id}")
    Call<Event> putEvent(@Path("id") Long id, @Body Event newEvent);

    @DELETE("event/delete/{id}")
    Call<Void> deleteEvent(@Path("id") Long id);

}
