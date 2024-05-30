package com.victorbarrozo.comidafacil.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victorbarrozo.comidafacil.db.MealDataBase
import com.victorbarrozo.comidafacil.pojo.CategoriaRefeicoes
import com.victorbarrozo.comidafacil.pojo.Category
import com.victorbarrozo.comidafacil.pojo.ListaCategoria
import com.victorbarrozo.comidafacil.pojo.ListaRefeicoes
import com.victorbarrozo.comidafacil.pojo.ListaTodasCategorias
import com.victorbarrozo.comidafacil.pojo.Meal
import com.victorbarrozo.comidafacil.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InicioViewModel(
    private val mealDataBase: MealDataBase
): ViewModel() {
    private var comidaAleatoriaLiveData= MutableLiveData<Meal>()
    private var itemPopularLiveData= MutableLiveData<List<CategoriaRefeicoes>>()
    private var todasCategoriasLiveData = MutableLiveData<List<Category>>()
    private var refeicoesFavoritasLiveData = mealDataBase.mealDao().getAllMeals()
    private var bottomSheetLiveData = MutableLiveData<Meal>()
    fun getComidaAleatoria() {
        RetrofitInstance.api.getComidaAleatoria().enqueue(object : Callback<ListaRefeicoes> {
            override fun onResponse(call: Call<ListaRefeicoes>, response: Response<ListaRefeicoes>) {
                if ( response.body() != null ) {
                    val comidaAleatoria: Meal = response.body()!!.meals[0]
                    //Log.d ( "test", "meal id ${comidaAleatoria.idMeal}")
                    comidaAleatoriaLiveData.value = comidaAleatoria

                }else{
                    return
                }
            }
            override fun onFailure(call: Call<ListaRefeicoes>, t: Throwable) {
                Log.d( "homeFragment", t.message.toString())
            }
        })
    }
    fun pegarItemPopular() {
        RetrofitInstance
            .api
            .pegarItemPopular( "Seafood" )
            .enqueue( object : Callback<ListaCategoria>{

                override fun onResponse(call: Call<ListaCategoria>, response: Response<ListaCategoria>) {
                if (response.body() != null) {
                    itemPopularLiveData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<ListaCategoria>, t: Throwable) {
                Log.d ( "home_fragment", t.message.toString())
            }

        })
    }

    fun pegarListaTodasCategorias(){
        RetrofitInstance.api.pegarCategorias().enqueue(object : Callback<ListaTodasCategorias>{
            override fun onResponse(
                call: Call<ListaTodasCategorias>,
                response: Response<ListaTodasCategorias>
            ) {
               /* response.body().let {listaTodasCategorias ->
                    todasCategoriasLiveData.postValue( listaTodasCategorias?.categories )

                }*/
                if ( response != null ) {
                    todasCategoriasLiveData.value = response.body()!!.categories

                }
            }

            override fun onFailure(call: Call<ListaTodasCategorias>, t: Throwable) {
                Log.d ( "home_fragment", t.message.toString())
           }
        })
    }
    fun getRefeicaoPorId(id: String) {
        RetrofitInstance.api.getDetalhesComidas(id).enqueue( object : Callback<ListaRefeicoes>{
            override fun onResponse(call: Call<ListaRefeicoes>, response: Response<ListaRefeicoes>) {
                val meal = response.body()?.meals?.first()
                meal?.let {meal->
                    bottomSheetLiveData.postValue(meal)
                }
            }

            override fun onFailure(call: Call<ListaRefeicoes>, t: Throwable) {
                Log.d("homeViewModel", t.message.toString())
            }


        })


    }
    fun deleteMeal(meal: Meal){
        viewModelScope.launch{
           mealDataBase.mealDao().delete(meal)
        }
    }
    fun insertMeal(meal : Meal){
        viewModelScope.launch {
            mealDataBase.mealDao().upsert( meal )
        }
    }
    fun observarComidaAleatoriaLiveData(): LiveData<Meal> {
        return comidaAleatoriaLiveData
    }
    fun observaritempopularLiveData(): LiveData<List<CategoriaRefeicoes>>{
        return itemPopularLiveData
    }

    fun observarTodasCategoriasLiveData(): LiveData<List<Category>>{
        return todasCategoriasLiveData
    }
    fun observeRefeicaoFavoridaLiveData(): LiveData<List<Meal>>{
        return refeicoesFavoritasLiveData
    }

    fun observeBottomSheetLiveData(): LiveData<Meal> = bottomSheetLiveData


}