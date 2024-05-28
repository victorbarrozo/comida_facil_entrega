package com.victorbarrozo.comidafacil.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.victorbarrozo.comidafacil.R
import com.victorbarrozo.comidafacil.db.MealDataBase
import com.victorbarrozo.comidafacil.viewModel.InicioViewModel
import com.victorbarrozo.comidafacil.viewModel.InicioViewModelFactory

class MainActivity : AppCompatActivity() {
     val viewModel: InicioViewModel by lazy {
        val mealDataBase = MealDataBase.getInstance(this)
         val iniciViewModelProviderFactory = InicioViewModelFactory(mealDataBase)
         ViewModelProvider(this, iniciViewModelProviderFactory)[InicioViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.btm_nav)
        val navController = Navigation.findNavController( this, R.id.host_fragment)

        NavigationUI.setupWithNavController( bottomNavigationView, navController )
    }
}