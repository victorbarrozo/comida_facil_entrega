package com.victorbarrozo.comidafacil.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.victorbarrozo.comidafacil.pojo.ListaComida
import com.victorbarrozo.comidafacil.pojo.Meal
import com.victorbarrozo.comidafacil.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InicioViewModel(): ViewModel() {
    private var comidaAleatoriaLiveData= MutableLiveData<Meal>()
    fun getComidaAleatoria() {
        RetrofitInstance.api.getComidaAleatoria().enqueue(object : Callback<ListaComida> {
            override fun onResponse(call: Call<ListaComida>, response: Response<ListaComida>) {
                if ( response.body() != null ) {
                    val comidaAleatoria: Meal = response.body()!!.meals[0]
                    //Log.d ( "test", "meal id ${comidaAleatoria.idMeal}")
                    comidaAleatoriaLiveData.value = comidaAleatoria

                }else{
                    return
                }
            }
            override fun onFailure(call: Call<ListaComida>, t: Throwable) {
                Log.d( "homeFragment", t.message.toString())
            }
        })
    }
    fun observarComidaAleatoriaLiveData(): LiveData<Meal> {
        return comidaAleatoriaLiveData
    }
}