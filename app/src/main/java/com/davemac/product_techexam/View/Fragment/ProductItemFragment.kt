package com.davemac.product_techexam.View.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.davemac.product_techexam.Factory.ProductFactory
import com.davemac.product_techexam.R
import com.davemac.product_techexam.Repository.ProductRepository
import com.davemac.product_techexam.Utils.Constant.TAG
import com.davemac.product_techexam.ViewModel.ProductViewModel
import com.davemac.product_techexam.databinding.FragmentProductDetailsBinding
import kotlinx.coroutines.delay

class ProductItemFragment: Fragment() {

    private var _binding: FragmentProductDetailsBinding?= null

    private val binding get() = _binding!!

    private var id: String ?= null

    private lateinit var productViewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)

        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         startShimmer()

        val productRepository = ProductRepository()

        val viewModelFactory  = ProductFactory(productRepository)

        productViewModel = ViewModelProvider(this, viewModelFactory).get(ProductViewModel::class.java)

        id = arguments?.getString(getString(R.string.productid))

        Log.d(TAG, "Get argument " + id)

        productViewModel.getProductItem(id!!.toLong())

        observeMain()
    }

    private fun observeMain(){

        productViewModel.productItem.observe(requireActivity(), Observer { info ->


            Thread.sleep(1000L)

            Log.d(TAG, "Get Product info " + info.data!!.title)
            Log.d(TAG, "Get Product info " + info.data!!.images[0])

            _binding!!.textViewProductName.text = info.data.title
            _binding!!.textViewProductRating.text = info.data.rating.toString()

            Glide
                .with(requireContext())
                .load(info.data.images[0])
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(_binding!!.imgViewProductImage)

            stopShimmer()

        })

        productViewModel.productErrorMsg.observe(requireActivity(), Observer { msg->
             _binding!!.linearContent.visibility = View.GONE
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun startShimmer(){
        _binding!!.shimmerEffect.startShimmer()
    }

    @SuppressLint("SuspiciousIndentation")
    private fun stopShimmer(){

        _binding!!.shimmerEffect.visibility = View.GONE
      _binding!!.linearContent.visibility = View.VISIBLE
        _binding!!.shimmerEffect.stopShimmer()
    }
}