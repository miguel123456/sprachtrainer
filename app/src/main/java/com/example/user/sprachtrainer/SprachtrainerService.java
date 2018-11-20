package com.example.user.sprachtrainer;

import com.example.user.sprachtrainer.models.Login;
import com.example.user.sprachtrainer.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SprachtrainerService {

    public static final String BASE_URL = "http://www.sprachtrainer.naylamp1.com/v1/";

    @POST("user/login")
    Call<User> login(@Body Login loginBody);
}
