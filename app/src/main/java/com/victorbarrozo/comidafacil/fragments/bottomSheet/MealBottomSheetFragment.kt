package com.victorbarrozo.comidafacil.fragments.bottomSheet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.victorbarrozo.comidafacil.activities.ComidaActivity
import com.victorbarrozo.comidafacil.activities.MainActivity
import com.victorbarrozo.comidafacil.databinding.FragmentMealBottomSheetBinding
import com.victorbarrozo.comidafacil.fragments.InicioFragment
import com.victorbarrozo.comidafacil.viewModel.InicioViewModel


private const val MEAL_ID = "param1"



class MealBottomSheetFragment : BottomSheetDialogFragment() {
    private var mealId: String? = null
    private var mealName: String? = null
    private var mealThumb: String? = null
    private lateinit var binding: FragmentMealBottomSheetBinding
    private lateinit var viewModel: InicioViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)
        }
        viewModel = (activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMealBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

        mealId?.let {
            viewModel.getRefeicaoPorId(it)
        }

        observeBottomSheet()
        onBootemSheetDialogClick()
    }
    private fun onBootemSheetDialogClick() {
        binding.bottomSheet.setOnClickListener {
            if ( mealName != null && mealThumb != null) {
                val intent = Intent(activity, ComidaActivity::class.java)

                intent.apply {
                    putExtra( InicioFragment.MEAL_ID, mealId)
                    putExtra( InicioFragment.MEAL_NAME, mealName)
                    putExtra( InicioFragment.MEAL_THUMB, mealThumb)
                }
                startActivity(intent)
            }
        }
    }
    private fun observeBottomSheet() {
        viewModel.observeBottomSheetLiveData().observe(viewLifecycleOwner, Observer {meal ->
            Glide.with(this).load(meal.strMealThumb).into(binding.imgBottomSheet)
            binding.tvBottomSheetArea.text = meal.strArea
            binding.tvBottomSheetCategory.text = meal.strCategory
            binding.tvBottomSheetName.text = meal.strMeal

            mealName = meal.strMeal
            mealThumb = meal.strMealThumb

        })
    }



    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            MealBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, param1)
                }
            }
    }
}