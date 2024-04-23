package com.victorbarrozo.comidafacil.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api: ComidaApi by lazy {

        Retrofit.Builder()
            .baseUrl( "https://www.themealdb.com/api/json/v1/1/" )
            .addConverterFactory( GsonConverterFactory.create())
            .build()
            .create( ComidaApi::class.java )

    }


}