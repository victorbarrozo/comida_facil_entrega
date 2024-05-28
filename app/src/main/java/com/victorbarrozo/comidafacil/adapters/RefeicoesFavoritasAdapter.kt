package com.victorbarrozo.comidafacil.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.victorbarrozo.comidafacil.databinding.ItemRefeicaoBinding
import com.victorbarrozo.comidafacil.pojo.Meal

class RefeicoesFavoritasAdapter: RecyclerView.Adapter
<RefeicoesFavoritasAdapter.RefeicoesFavoritasAdapterViewHolder>() {

    inner class RefeicoesFavoritasAdapterViewHolder( val binding: ItemRefeicaoBinding):
    RecyclerView.ViewHolder( binding.root )

   private val diffutil = object  : DiffUtil.ItemCallback<Meal>(){
       override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
           return oldItem.idMeal == newItem.idMeal
       }

       override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
           return  oldItem == newItem
       }
   }
    val differ = AsyncListDiffer( this, diffutil )
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RefeicoesFavoritasAdapterViewHolder {
        return  RefeicoesFavoritasAdapterViewHolder(
            ItemRefeicaoBinding.inflate(
                LayoutInflater.from( parent.context ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RefeicoesFavoritasAdapterViewHolder, position: Int) {
        val refeicao = differ.currentList[position]
        Glide.with(holder.itemView).load( refeicao.strMealThumb ).into( holder.binding.imgRefeicao )
        holder.binding.tvNomeRefeicao.text = refeicao.strMeal
    }
    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}