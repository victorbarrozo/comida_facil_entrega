package com.victorbarrozo.comidafacil.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.victorbarrozo.comidafacil.R
import com.victorbarrozo.comidafacil.databinding.ActivityCategoriaRefeicoesBinding
import com.victorbarrozo.comidafacil.viewModel.CategoriaRefeicoesViewModel

class CategoriaRefeicoesActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityCategoriaRefeicoesBinding.inflate( layoutInflater )
    }
    lateinit var categoriaRefeicaoViewModel : CategoriaRefeicoesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        categoriaRefeicaoViewModel = ViewModelProvider(this)[ CategoriaRefeicoesViewModel::class.java ]
        
    }
}