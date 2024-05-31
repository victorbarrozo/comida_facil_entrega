package com.victorbarrozo.comidafacil.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.victorbarrozo.comidafacil.R
import com.victorbarrozo.comidafacil.activities.CategoriaRefeicoesActivity
import com.victorbarrozo.comidafacil.activities.ComidaActivity
import com.victorbarrozo.comidafacil.activities.MainActivity
import com.victorbarrozo.comidafacil.adapters.MaisPopularesAdapters
import com.victorbarrozo.comidafacil.adapters.TodasCategoriasAdapter
import com.victorbarrozo.comidafacil.databinding.FragmentInicioBinding
import com.victorbarrozo.comidafacil.fragments.bottomSheet.MealBottomSheetFragment
import com.victorbarrozo.comidafacil.pojo.CategoriaRefeicoes
import com.victorbarrozo.comidafacil.pojo.Meal
import com.victorbarrozo.comidafacil.viewModel.InicioViewModel


class InicioFragment : Fragment() {
    private lateinit var binding: FragmentInicioBinding
    private lateinit var viewModel: InicioViewModel
    private lateinit var comidaAleatoria: Meal
    private lateinit var itemPopularAdapter: MaisPopularesAdapters
    private lateinit var todasCategoriasAdapter: TodasCategoriasAdapter
    companion object{
        const val MEAL_ID = "com.victorbarrozo.comidafacil.fragments.idMeal"
        const val MEAL_NAME = "com.victorbarrozo.comidafacil.fragments.nameMeal"
        const val MEAL_THUMB = "com.victorbarrozo.comidafacil.fragments.thumbMeal"
        const val CATEGORIA_NOME = "com.victorbarrozo.comidafacil.fragments.categoriaNome"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //inicioMvvm = ViewModelProvider(this)[InicioViewModel::class.java]
        viewModel = (activity as MainActivity).viewModel

        itemPopularAdapter = MaisPopularesAdapters()
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
        preparaItemPopularesRecyclerView()

        observarComidaAleatoria()
        onComidaAleatoriaClick()

        viewModel.pegarItemPopular()
        observarItemPopularLiveData()
        aoClicarItemPopular()

        prepararCategoriasRecyclerView()
        viewModel.pegarListaTodasCategorias()
        observarTodasCategoriasLiveData()
        aoClicarCategoria()

        aoLogoClickEmPopular()

        aoClicarNoProcurar()


    }

    private fun aoClicarNoProcurar() {
        binding.imgProcurar.setOnClickListener {
            findNavController().navigate(R.id.action_inicioFragment_to_procurarFragment)
        }
    }

    private fun aoLogoClickEmPopular() {
        itemPopularAdapter.onLongItemClick = {meal->
            val mealBottomSheetFragment = MealBottomSheetFragment.newInstance(meal.idMeal)
            mealBottomSheetFragment.show(childFragmentManager, "Meal Info")
        }
    }

    private fun aoClicarCategoria() {
        todasCategoriasAdapter.aoClicaricone = {category ->
            val intent = Intent( activity, CategoriaRefeicoesActivity::class.java )
            intent.putExtra( CATEGORIA_NOME, category.strCategory )
            startActivity( intent )

        }
    }

    private fun prepararCategoriasRecyclerView() {
        todasCategoriasAdapter = TodasCategoriasAdapter()

        binding.recCategorias.apply {
            layoutManager = GridLayoutManager(
                context,3,GridLayoutManager.VERTICAL, false
            )
            adapter = todasCategoriasAdapter
        }
    }

    private fun observarTodasCategoriasLiveData() {
        viewModel.observarTodasCategoriasLiveData().observe( viewLifecycleOwner, Observer { categorias->
                todasCategoriasAdapter.setListaTodasCategorias( categorias )
        } )
    }

    private fun aoClicarItemPopular() {
        itemPopularAdapter.onItemClick = {refeicoes ->
            val intent = Intent( activity,ComidaActivity::class.java )
            intent.putExtra( MEAL_ID, refeicoes.idMeal )
            intent.putExtra( MEAL_NAME, refeicoes.strMeal )
            intent.putExtra( MEAL_THUMB, refeicoes.strMealThumb )
            startActivity( intent )
        }
    }

    private fun preparaItemPopularesRecyclerView() {
        binding.recComidasPopulares.apply {
            layoutManager = LinearLayoutManager( activity, LinearLayoutManager.HORIZONTAL,false )
            adapter = itemPopularAdapter
        }
    }

    private fun observarItemPopularLiveData() {
        viewModel.observaritempopularLiveData().observe(viewLifecycleOwner,
        { listaRefeicoes ->
            itemPopularAdapter.setRefeicao( listaRefeicoes = listaRefeicoes as ArrayList<CategoriaRefeicoes> )

        })
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
        viewModel.observarComidaAleatoriaLiveData().observe( viewLifecycleOwner)
        {meal->
            Glide.with(this@InicioFragment)
                .load(meal!!.strMealThumb)
                .into(binding.imgComidaAleatoria)
            this.comidaAleatoria = meal
        }
    }
}