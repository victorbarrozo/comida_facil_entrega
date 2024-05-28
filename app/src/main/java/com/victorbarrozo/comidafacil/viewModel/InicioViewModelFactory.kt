package com.victorbarrozo.comidafacil.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.victorbarrozo.comidafacil.db.MealDataBase

class InicioViewModelFactory(
    private val mealDataBase: MealDataBase
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return InicioViewModel(mealDataBase) as T
    }


}