package com.victorbarrozo.comidafacil.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.victorbarrozo.comidafacil.adapters.CategoriaRefeicoesAdapter
import com.victorbarrozo.comidafacil.databinding.ActivityCategoriaRefeicoesBinding
import com.victorbarrozo.comidafacil.fragments.InicioFragment
import com.victorbarrozo.comidafacil.viewModel.CategoriaRefeicoesViewModel

class CategoriaRefeicoesActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityCategoriaRefeicoesBinding.inflate( layoutInflater )
    }
    lateinit var categoriaRefeicaoViewModel : CategoriaRefeicoesViewModel
    lateinit var categoriaRefeicoesAdapter: CategoriaRefeicoesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        prepararRecyclarView()
        categoriaRefeicaoViewModel = ViewModelProvider(this)[ CategoriaRefeicoesViewModel::class.java ]
        categoriaRefeicaoViewModel.pegarRefeicaoPorCategoria( intent.getStringExtra( InicioFragment.CATEGORIA_NOME )!! )
        categoriaRefeicaoViewModel.observarRefeicoesLiveData().observe( this) { listaRefeicoes ->
            binding.tvCategoriaContagem.text = listaRefeicoes.size.toString()
            categoriaRefeicoesAdapter.setListaRefeicoes( listaRefeicoes )
        }
    }

    private fun prepararRecyclarView() {
        categoriaRefeicoesAdapter = CategoriaRefeicoesAdapter()
        binding.rvRefeicoes.apply {
            layoutManager = GridLayoutManager( context,2,GridLayoutManager.VERTICAL,false )
            adapter= categoriaRefeicoesAdapter
        }
    }
}