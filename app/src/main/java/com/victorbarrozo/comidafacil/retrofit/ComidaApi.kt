package com.victorbarrozo.comidafacil.retrofit

import com.victorbarrozo.comidafacil.pojo.ListaComida
import com.victorbarrozo.comidafacil.pojo.Meal
import retrofit2.Call
import retrofit2.http.GET

interface ComidaApi {
    @GET("random.php")
    fun getComidaAleatoria(): Call<ListaComida>

}