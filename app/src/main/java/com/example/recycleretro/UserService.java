package com.example.recycleretro;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface UserService {
    @GET("/test/myapi")
    Call<ResponseBody> login();
}
