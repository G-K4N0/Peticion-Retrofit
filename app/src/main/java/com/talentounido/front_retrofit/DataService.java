package com.talentounido.front_retrofit;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DataService {

    @GET("users")
    Call<List<Person>> getPersons();

    @POST("users")
    Call<Person> createPerson(@Body Person person);

    @PUT("users/{id}")
    Call<Person> putUser(@Path("id") String id, @Body Person person);

    @DELETE("users/{id}")
    Call<Person> deleteUser(@Path("id") String id);


}
