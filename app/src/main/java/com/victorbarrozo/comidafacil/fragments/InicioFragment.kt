package com.victorbarrozo.comidafacil.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.victorbarrozo.comidafacil.activities.ComidaActivity
import com.victorbarrozo.comidafacil.databinding.FragmentInicioBinding
import com.victorbarrozo.comidafacil.pojo.Meal
import com.victorbarrozo.comidafacil.viewModel.InicioViewModel



class InicioFragment : Fragment() {
    private lateinit var binding: FragmentInicioBinding
    private lateinit var inicioMvvm: InicioViewModel
    private lateinit var comidaAleatoria: Meal

    companion object{
        const val MEAL_ID = "com.victorbarrozo.comidafacil.fragments.idMeal"
        const val MEAL_NAME = "com.victorbarrozo.comidafacil.fragments.nameMeal"
        const val MEAL_THUMB = "com.victorbarrozo.comidafacil.fragments.thumbMeal"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inicioMvvm = ViewModelProvider(this).get( InicioViewModel::class.java )
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

        inicioMvvm.getComidaAleatoria()
        observarComidaAleatoria()
        onComidaAleatoriaClick()
    }

    private fun onComidaAleatoriaClick() {
        binding.cardComidaAleatoria.setOnClickListener {
            val intent = Intent ( activity, ComidaActivity::class.java)
            intent.putExtra( MEAL_ID,comidaAleatoria.idMeal )
            intent.putExtra( MEAL_NAME,comidaAleatoria.strMeal )
            intent.putExtra( MEAL_THUMB,comidaAleatoria.strMealThumb )
            startActivity( intent )
        }
    }

    private fun observarComidaAleatoria() {
        inicioMvvm.observarComidaAleatoriaLiveData().observe( viewLifecycleOwner)
        {meal->
            Glide.with(this@InicioFragment)
                .load(meal!!.strMealThumb)
                .into(binding.imgComidaAleatoria)
            this.comidaAleatoria = meal
        }
    }
}