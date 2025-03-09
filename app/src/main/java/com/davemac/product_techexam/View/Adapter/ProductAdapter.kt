package com.davemac.product_techexam.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.davemac.product_techexam.Model.Products.Product
import com.davemac.product_techexam.R
import com.davemac.product_techexam.Utils.Constant.TAG
import com.davemac.product_techexam.databinding.ItemProductViewBinding

class ProductAdapter(private val context: Context): RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private lateinit var binding: ItemProductViewBinding

    private val productItem = arrayListOf<Product>()

    var onItemClick : ((Product) -> Unit)? =  null

     inner class ViewHolder() : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Product) {
           binding.apply {
               textViewProductName.text = item.title
               textViewProductDescription.text = item.description
               textViewProductRating.text = item.rating.toString()
//                textViewLocation.text = item.location.name
                Glide
                    .with(context)
                    .load(item.images[0])
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imgViewProductImage)

           }
       }
   }

    fun onAddItems(item: Product){
        Log.d(TAG, "Adapter items " + item.title)
        productItem.add(item)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemProductViewBinding.inflate(inflater, parent, false)
        return ViewHolder()
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "Get item count " + productItem.size)
        return productItem.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentProduct = productItem[position]

        holder.bind(currentProduct)
        holder.setIsRecyclable(false)

        holder.itemView.setOnClickListener(){
            onItemClick?.invoke(currentProduct)
        }
    }
}