package com.victorbarrozo.comidafacil.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victorbarrozo.comidafacil.db.MealDataBase
import com.victorbarrozo.comidafacil.pojo.ListaRefeicoes
import com.victorbarrozo.comidafacil.pojo.Meal
import com.victorbarrozo.comidafacil.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(
    val mealDatabase: MealDataBase
): ViewModel() {
    private var mealDetalhesLiveData = MutableLiveData<Meal>()

    fun getDetalhesComidas( id: String ){
        RetrofitInstance.api.getDetalhesComidas(id).enqueue(object: Callback<ListaRefeicoes>{
            override fun onResponse(call: Call<ListaRefeicoes>, response: Response<ListaRefeicoes>) {
                if ( response.body() != null ) {
                    mealDetalhesLiveData.value = response.body()!!.meals[0]
                }else
                    return
            }

            override fun onFailure(call: Call<ListaRefeicoes>, t: Throwable) {
                Log.d( "meal_activity", t.message.toString() )
            }

        })
    }

    fun observarDetalhesLiveData(): LiveData<Meal> {
        return mealDetalhesLiveData
    }
    fun insertMeal(meal : Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().upsert( meal )
        }
    }
} 