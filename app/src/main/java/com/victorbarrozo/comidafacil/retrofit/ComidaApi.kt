package com.victorbarrozo.comidafacil.retrofit

import com.victorbarrozo.comidafacil.pojo.ListaCategoria
import com.victorbarrozo.comidafacil.pojo.ListaRefeicoes
import com.victorbarrozo.comidafacil.pojo.ListaTodasCategorias
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ComidaApi {
    @GET("random.php")
    fun getComidaAleatoria(): Call<ListaRefeicoes>

    @GET ("lookup.php")
    fun getDetalhesComidas( @Query("i") id: String ): Call<ListaRefeicoes>

    @GET ("filter.php")
    fun pegarItemPopular(@Query("c") categoriaNome: String): Call<ListaCategoria>

    @GET ("categories.php")
    fun pegarCategorias(): Call<ListaTodasCategorias>

    @GET ("filter.php")
    fun pegarRefeicoesPorCategoria (@Query ("c") categoriaNome: String ): Call<ListaCategoria>

    @GET ("search.php")
    fun shearchMeal(@Query("s") searchQuery: String): Call<ListaRefeicoes>


}