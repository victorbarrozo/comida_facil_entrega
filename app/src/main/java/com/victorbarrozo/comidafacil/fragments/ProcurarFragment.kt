package com.victorbarrozo.comidafacil.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.victorbarrozo.comidafacil.activities.MainActivity
import com.victorbarrozo.comidafacil.adapters.RefeicoesFavoritasAdapter
import com.victorbarrozo.comidafacil.databinding.FragmentProcurarBinding
import com.victorbarrozo.comidafacil.viewModel.InicioViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProcurarFragment : Fragment() {
    private lateinit var binding: FragmentProcurarBinding
    private lateinit var viewModel : InicioViewModel
    private lateinit var procurarRecyclerViewAdapter: RefeicoesFavoritasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProcurarBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepararRecyclerView()

        binding.imgProcurar.setOnClickListener {
            procurarRefeicao()
        }

        observarProcurarRefeicaoLiveData()


        var procurarJob: Job? = null
        binding.edProcurar.addTextChangedListener {searchQuery ->
            procurarJob?.cancel()
            procurarJob = lifecycleScope.launch {
                delay(500)
                viewModel.procurarRefeicoes(searchQuery.toString())
            }
        }
    }

    private fun observarProcurarRefeicaoLiveData() {
        viewModel.observarProcurarMealsLiveData().observe( viewLifecycleOwner, Observer {listaComidas ->
            procurarRecyclerViewAdapter.differ.submitList(listaComidas)
        })
    }

    private fun procurarRefeicao() {
        val searchQuery = binding.edProcurar.text.toString()
        if ( searchQuery.isNotEmpty() ) {
            viewModel.procurarRefeicoes(searchQuery)
        }
    }

    private fun prepararRecyclerView() {
        procurarRecyclerViewAdapter = RefeicoesFavoritasAdapter()
        binding.rvProcurar.apply {
            layoutManager = GridLayoutManager( context, 2 , GridLayoutManager.VERTICAL,false )
            adapter = procurarRecyclerViewAdapter
        }
    }
}