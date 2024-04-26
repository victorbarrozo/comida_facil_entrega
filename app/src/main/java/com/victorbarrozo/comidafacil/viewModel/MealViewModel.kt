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

class MealViewModel(): ViewModel() {
    private var mealDetalhesLiveData = MutableLiveData<Meal>()

    fun getDetalhesComidas( id: String ){
        RetrofitInstance.api.getDetalhesComidas(id).enqueue(object: Callback<ListaComida>{
            override fun onResponse(call: Call<ListaComida>, response: Response<ListaComida>) {
                if ( response.body() != null ) {
                    mealDetalhesLiveData.value = response.body()!!.meals[0]
                }else
                    return
            }

            override fun onFailure(call: Call<ListaComida>, t: Throwable) {
                Log.d( "meal_activity", t.message.toString() )
            }

        })
    }

    fun observarDetalhesLiveData(): LiveData<Meal> {
        return mealDetalhesLiveData
    }

}