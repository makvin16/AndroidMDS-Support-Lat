package com.zm.mds.mds_support.api;


import com.zm.mds.mds_support.model.Card;
import com.zm.mds.mds_support.model.Shop;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by moska on 18.10.2017.
 */

public interface ApiService {
    @FormUrlEncoded
    @POST("/api/shop/byPass")
    Call<Shop> apiShopPassword(
            @Field("pas") String pass
    );

    @GET("/api/organization/{id}/{barcode}")
    Call<Card> apiBarcode(
            @Path("id") String id,
            @Path("barcode") String barcode
    );

}
