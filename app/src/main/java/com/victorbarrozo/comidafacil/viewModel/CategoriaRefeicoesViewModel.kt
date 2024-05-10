package com.victorbarrozo.comidafacil.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.victorbarrozo.comidafacil.pojo.CategoriaRefeicoes
import com.victorbarrozo.comidafacil.pojo.ListaCategoria
import com.victorbarrozo.comidafacil.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriaRefeicoesViewModel : ViewModel() {
    val refeicoesLiveData = MutableLiveData<List<CategoriaRefeicoes>>()
    fun pegarRefeicaoPorCategoria(categoriaNome: String) {

        RetrofitInstance.api.pegarRefeicoesPorCategoria(categoriaNome)
            .enqueue(object : Callback<ListaCategoria> {
                override fun onResponse(
                    call: Call<ListaCategoria>,
                    response: Response<ListaCategoria>
                ) {
                    response.body().let { listaRefeicoes ->
                        refeicoesLiveData.value = listaRefeicoes?.meals

                    }
                }

                override fun onFailure(call: Call<ListaCategoria>, t: Throwable) {
                    Log.d("refeicoesCategoria", t.message.toString())
                }

            })
    }
    fun observarRefeicoesLiveData(): LiveData <List<CategoriaRefeicoes>> {
    return refeicoesLiveData
    }
}