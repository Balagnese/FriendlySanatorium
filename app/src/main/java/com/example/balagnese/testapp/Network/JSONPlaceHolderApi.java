package com.example.balagnese.testapp.Network;

import com.example.balagnese.testapp.DataTypes.Auth;
import com.example.balagnese.testapp.DataTypes.AuthResponse;
import com.example.balagnese.testapp.DataTypes.Client;
import com.example.balagnese.testapp.DataTypes.ClientProcedure;
import com.example.balagnese.testapp.DataTypes.ClientSelectedDishModel;
import com.example.balagnese.testapp.DataTypes.PostResponse;
import com.example.balagnese.testapp.DataTypes.Procedure;
import com.example.balagnese.testapp.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JSONPlaceHolderApi {
//    @GET("/posts/{id}")
//    public Call<Post> getPostWithID(@Path("id") int id);
//
//    @GET("/posts")
//    public Call<List<Post>> getAllPosts();
//
//    @GET("/posts")
//    public Call<List<Post>> getPostOfUser(@Query("userId") int id);
//
//    @POST("/posts")
//    public Call<Post> postData(@Body Post data);

    @GET ("/api/clients/me")
    public Call<Client> getCurrentClient(@Header("Authorization") String authorization);

    @POST("/api/auth/login")
    public Call<AuthResponse> logIn(@Body Auth auth);

    @POST ("/api/auth/logout")
    public Call<PostResponse> logOut(@Header("Authorization") String authorisation);

    @GET("/api/clients/me/children")
    public Call<List<Client>> getCurrentClientChildren(@Header("Authorization") String authorization);

//    @GET("/api/clients/{childId")
//    public Call<Client> getClientById(@Body int clientId);

    @GET("/api/clients/me/procedures")
    public Call<List<ClientProcedure>> getCurrentClientProcedures(@Header("Authorization") String authorization);

    @GET("/api/clients/{client_public_id}/procedures")
    public Call<List<ClientProcedure>> getChildProcedures(@Header("Authorization") String authorisation, @Path("client_public_id") String client_public_id);

    @GET("/api/procedures/{procedure_id}")
    public Call<Procedure> getProcedure(@Path("procedure_id") int procedure_id);

    @GET("/api/clients/me/dishes/{date}")
    public Call<ClientSelectedDishModel> getClientMenu(@Header("Authorization") String authorization, @Path("date") String date);

    @POST("/api/clients/me/dishes")
    public Call<PostResponse> selectDish(@Header("Authorization") String authorization);

    @GET("/api/clients/{public_id}/dishes/{date}")
    public Call<ClientSelectedDishModel> getChildMenu(@Header("Authorization") String authorization,
                                                      @Path("date") String date, @Path("public_id") String client_public_id);

    @POST("/api/clients/me/dishes")
    public Call<PostResponse> selectClientDish(@Header("Authorization") String authorization, @Body int daily_menu_id,
                                               @Body String meal_tag, @Body int dishes_group_id, @Body int dish_id);

    @POST("/clients/{public_id}/dishes")
    public Call<PostResponse> selectChildDish(@Header("Authorization") String authorization, @Path("public_id") int public_id,
                                              @Body int daily_menu_id, @Body String meal_tag, @Body int dishes_group_id, @Body int dish_id);
}
