package com.victorbarrozo.comidafacil.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.victorbarrozo.comidafacil.R
import com.victorbarrozo.comidafacil.databinding.FragmentInicioBinding
import com.victorbarrozo.comidafacil.pojo.ListaComida
import com.victorbarrozo.comidafacil.pojo.Meal
import com.victorbarrozo.comidafacil.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class InicioFragment : Fragment() {
    private lateinit var binding: FragmentInicioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInicioBinding.inflate( inflater, container, false )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        RetrofitInstance.api.getComidaAleatoria().enqueue(object : Callback< ListaComida > {
            override fun onResponse(call: Call<ListaComida>, response: Response<ListaComida>) {
                if ( response.body() != null ) {
                    val comidaAleatoria: Meal = response.body()!!.meals[0]
                    //Log.d ( "test", "meal id ${comidaAleatoria.idMeal}")
                    Glide.with( this@InicioFragment )
                        .load( comidaAleatoria.strMealThumb )
                        .into( binding.imgComidaAleatoria )


                }else{
                    return
                }
            }

            override fun onFailure(call: Call<ListaComida>, t: Throwable) {
                Log.d( "homeFragment", t.message.toString())
            }

        })
    }
}