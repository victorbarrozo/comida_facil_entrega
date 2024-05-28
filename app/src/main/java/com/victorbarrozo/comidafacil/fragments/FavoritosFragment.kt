package com.victorbarrozo.comidafacil.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.victorbarrozo.comidafacil.activities.MainActivity
import com.victorbarrozo.comidafacil.adapters.RefeicoesFavoritasAdapter
import com.victorbarrozo.comidafacil.databinding.FragmentFavoritosBinding
import com.victorbarrozo.comidafacil.viewModel.InicioViewModel


class FavoritosFragment : Fragment() {
    private lateinit var binding: FragmentFavoritosBinding
    private lateinit var viewModel: InicioViewModel
    private lateinit var favoritesAdapter: RefeicoesFavoritasAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ( activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritosBinding.inflate( inflater )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepararRecyclerView()
        observeFavoritos()


    }

    private fun prepararRecyclerView() {
        favoritesAdapter = RefeicoesFavoritasAdapter()
        binding.rvFavoritos.apply {
            layoutManager= GridLayoutManager( context,2, GridLayoutManager.VERTICAL,false )
            adapter = favoritesAdapter
        }
    }

    private fun observeFavoritos() {
        viewModel.observeRefeicaoFavoridaLiveData().observe( viewLifecycleOwner, { meal ->
            favoritesAdapter.differ.submitList( meal )

        })
    }
}