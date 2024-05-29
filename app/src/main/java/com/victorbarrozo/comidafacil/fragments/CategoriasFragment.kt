package com.victorbarrozo.comidafacil.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.victorbarrozo.comidafacil.activities.MainActivity
import com.victorbarrozo.comidafacil.adapters.TodasCategoriasAdapter
import com.victorbarrozo.comidafacil.databinding.FragmentCategoriasBinding
import com.victorbarrozo.comidafacil.viewModel.InicioViewModel


class CategoriasFragment : Fragment() {
    private lateinit var binding: FragmentCategoriasBinding
    private lateinit var categoriasAdapter:TodasCategoriasAdapter
    private lateinit var viewModel : InicioViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoriasBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepararRecyclerView()

        observarCategorias()
    }

    private fun observarCategorias() {
        viewModel.observarTodasCategoriasLiveData().observe(viewLifecycleOwner, Observer {categorias->
            categoriasAdapter.setListaTodasCategorias(categorias)

        })
    }

    private fun prepararRecyclerView() {
        categoriasAdapter =TodasCategoriasAdapter()
        binding.rvCategorias.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter = categoriasAdapter
        }
    }

}