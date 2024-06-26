package com.victorbarrozo.comidafacil.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.victorbarrozo.comidafacil.R
import com.victorbarrozo.comidafacil.databinding.ActivityComidaBinding
import com.victorbarrozo.comidafacil.db.MealDataBase
import com.victorbarrozo.comidafacil.fragments.InicioFragment
import com.victorbarrozo.comidafacil.pojo.Meal
import com.victorbarrozo.comidafacil.viewModel.MealViewModel
import com.victorbarrozo.comidafacil.viewModel.MealViewModelFactory

class ComidaActivity : AppCompatActivity() {
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var youtubeLink: String
    private lateinit var mealMvvm: MealViewModel

    private val binding by lazy {
        ActivityComidaBinding.inflate( layoutInflater )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        
        val mealDataBase= MealDataBase.getInstance( this )
        val viewModelFactory = MealViewModelFactory( mealDataBase )
        mealMvvm = ViewModelProvider( this, viewModelFactory )[MealViewModel::class.java]

        //mealMvvm = ViewModelProvider(this)[MealViewModel::class.java]

        pegarInformacoesDasIntents()

        configurarInformacoesNaView()

        loadingCase()
        mealMvvm.getDetalhesComidas( mealId )
        observarDetalhesLiveData()

        aoClicarNaImagemYoutube()
        aoClicarEmFavoritos()
    }

    private fun aoClicarEmFavoritos() {
        binding.btnAddToFavorites.setOnClickListener { 
           mealToSave?.let {
               mealMvvm.insertMeal(it)
               Toast.makeText(this, "Refeição Salva", Toast.LENGTH_SHORT).show()
           }
             
        }
    }

    private fun aoClicarNaImagemYoutube() {
        binding.ivYoutube.setOnClickListener {
            val intent = Intent( Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity( intent )
        }
    }
    private var mealToSave:Meal?=null 
    private fun observarDetalhesLiveData() {
        mealMvvm.observarDetalhesLiveData().observe( this, object : Observer<Meal>{
            override fun onChanged(value: Meal) {
                onResponseCase()
                val meal = value
                mealToSave=meal

                binding.tvCategoria.text = "Categoria: ${meal!!.strCategory}"
                binding.tvArea.text = "Área: ${meal.strArea}"
                binding.tvTextCompleto.text = meal.strInstructions
                youtubeLink = meal.strYoutube.toString()
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
    private fun loadingCase() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnAddToFavorites.visibility = View.INVISIBLE
        binding.tvInstrucoes.visibility = View.INVISIBLE
        binding.tvCategoria.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.ivYoutube.visibility = View.INVISIBLE
    }

    private fun onResponseCase() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnAddToFavorites.visibility = View.VISIBLE
        binding.tvInstrucoes.visibility = View.VISIBLE
        binding.tvCategoria.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.ivYoutube.visibility = View.VISIBLE

    }
}