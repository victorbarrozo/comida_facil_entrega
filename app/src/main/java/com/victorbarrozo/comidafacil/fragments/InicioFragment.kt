package com.victorbarrozo.comidafacil.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.victorbarrozo.comidafacil.databinding.FragmentInicioBinding
import com.victorbarrozo.comidafacil.pojo.Meal
import com.victorbarrozo.comidafacil.viewModel.InicioViewModel



class InicioFragment : Fragment() {
    private lateinit var binding: FragmentInicioBinding
    private lateinit var inicioMvvm: InicioViewModel

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
    }

    private fun observarComidaAleatoria() {
        inicioMvvm.observarComidaAleatoriaLiveData().observe( viewLifecycleOwner,object : Observer<Meal> {
            override fun onChanged(value: Meal) {
                Glide.with( this@InicioFragment )
                    .load( value.strMealThumb )
                    .into( binding.imgComidaAleatoria )
            }

        })
    }
}