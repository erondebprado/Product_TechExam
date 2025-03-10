package com.davemac.product_techexam.View.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davemac.product_techexam.Model.Categories.CategoriesModel
import com.davemac.product_techexam.Utils.Constant.TAG
import com.davemac.product_techexam.databinding.ItemCategoryViewBinding

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    private lateinit var binding: ItemCategoryViewBinding

    private val categoryItem = arrayListOf<CategoriesModel>()

    inner class ViewHolder() : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CategoriesModel) {
            binding.apply {
                textCategory.text = item.name
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemCategoryViewBinding.inflate(inflater, parent, false)
        return ViewHolder()
    }

    override fun getItemCount(): Int {
        return categoryItem.size
    }

    override fun onBindViewHolder(holder: CategoriesAdapter.ViewHolder, position: Int) {
        val currentCategory = categoryItem[position]

        holder.bind(currentCategory)
        holder.setIsRecyclable(false)
    }

    fun onAddItems(item: CategoriesModel){
        categoryItem.add(item)

        notifyDataSetChanged()
    }
}