package com.victorbarrozo.comidafacil.activities

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.victorbarrozo.comidafacil.R
import com.victorbarrozo.comidafacil.databinding.ActivityComidaBinding
import com.victorbarrozo.comidafacil.databinding.FragmentInicioBinding
import com.victorbarrozo.comidafacil.fragments.InicioFragment
import com.victorbarrozo.comidafacil.pojo.Meal
import com.victorbarrozo.comidafacil.viewModel.MealViewModel

class ComidaActivity : AppCompatActivity() {
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var mealMvvm: MealViewModel

    private val binding by lazy {
        ActivityComidaBinding.inflate( layoutInflater )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        mealMvvm = ViewModelProvider(this)[MealViewModel::class.java]

        pegarInformacoesDasIntents()

        configurarInformacoesNaView()

        mealMvvm.getDetalhesComidas( mealId )
        observarDetalhesLiveData()
    }

    private fun observarDetalhesLiveData() {
        mealMvvm.observarDetalhesLiveData().observe( this, object : Observer<Meal>{
            override fun onChanged(value: Meal) {
                val meal = value

                binding.tvCategoria.text = "Categoria: ${meal!!.strCategory}"
                binding.tvArea.text = "Área: ${meal.strArea}"
                binding.tvTextCompleto.text = meal.strInstructions
            }
        })
    }

    private fun configurarInformacoesNaView() {
        Glide.with( applicationContext )
            .load( mealThumb )
            .into( binding.imgComidaDetalhes )

       binding.collapsingToobar.title = mealName
        binding.collapsingToobar.setCollapsedTitleTextColor( resources.getColor( R.color.white ) )
        binding.collapsingToobar.setExpandedTitleColor( resources.getColor( R.color.white ) )
    }

    private fun pegarInformacoesDasIntents() {
        val intent = intent
        mealId = intent.getStringExtra(InicioFragment.MEAL_ID )!!
        mealName = intent.getStringExtra(InicioFragment.MEAL_NAME )!!
        mealThumb = intent.getStringExtra(InicioFragment.MEAL_THUMB )!!
    }
}