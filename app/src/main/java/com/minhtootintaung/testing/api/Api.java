package com.minhtootintaung.testing.api;

import com.minhtootintaung.testing.Model.AttractionModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    @GET("lists")
    Call<List<AttractionModel>>getlist();


}
