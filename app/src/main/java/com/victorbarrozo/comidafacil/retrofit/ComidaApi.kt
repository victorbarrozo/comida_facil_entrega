package com.victorbarrozo.comidafacil.retrofit

import com.victorbarrozo.comidafacil.pojo.ListaComida
import com.victorbarrozo.comidafacil.pojo.Meal
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ComidaApi {
    @GET("random.php")
    fun getComidaAleatoria(): Call<ListaComida>

    @GET ("lookup.php?")
    fun getDetalhesComidas( @Query("i") id: String ): Call<ListaComida>

}