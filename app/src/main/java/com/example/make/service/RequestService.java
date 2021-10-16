package com.example.make.service;

import com.example.make.service.dto.HostDTO;
import com.example.make.service.dto.LoginDTO;
import com.example.make.service.dto.MakeDTO;
import com.example.make.service.dto.UserDTO;
import com.example.make.service.dto.UserListDTO;

import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RequestService {

    @POST("/login")
    @FormUrlEncoded
    Call<LoginDTO> login(@FieldMap HashMap<String, String> map);

    @POST("/sign-up")
    @FormUrlEncoded
    Call<MakeDTO> make(@FieldMap HashMap<String, String> map);

    @POST("/host")
    @FormUrlEncoded
    Call<HostDTO> host(@FieldMap HashMap<String, Object> map);

    @POST("/passenger")
    @FormUrlEncoded
    Call<UserDTO> passenger(@FieldMap HashMap<String, Object> map);

    @GET("/passenger/list")
    Call<UserListDTO> listPassenger(@Query("hostId") Long hostId);
}
