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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.davemac.product_techexam.Factory.ProductFactory
import com.davemac.product_techexam.R
import com.davemac.product_techexam.Repository.ProductRepository
import com.davemac.product_techexam.Utils.Constant.TAG
import com.davemac.product_techexam.Utils.DataStatus
import com.davemac.product_techexam.Utils.isVisible
import com.davemac.product_techexam.View.Adapter.CategoriesAdapter
import com.davemac.product_techexam.ViewModel.ProductViewModel
import com.davemac.product_techexam.databinding.FragmentProductDetailsBinding
import kotlinx.coroutines.launch
import java.util.stream.Collectors



class ProductItemFragment: Fragment() {

    private var adapter: CategoriesAdapter?= null

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

        val productRepository = ProductRepository(requireContext())

        val viewModelFactory  = ProductFactory(productRepository, requireContext())

        productViewModel = ViewModelProvider(this, viewModelFactory).get(ProductViewModel::class.java)

        val linearLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
        _binding!!.rvCategories.layoutManager = linearLayoutManager


        if (adapter == null){
            adapter = CategoriesAdapter()

        }
        _binding!!.rvCategories.adapter = adapter

        id = arguments?.getString(getString(R.string.productid))

        Log.d(TAG, "Get argument " + id)

        lifecycleScope.launch {
                    binding.apply {

                        productViewModel.getProductItem(id!!.toLong())
                        productViewModel.loadCategories()

                        productViewModel.categoryList.observe(viewLifecycleOwner){

                            Log.d(TAG, "Category UI " + it.status)
                            when(it.status){
                                DataStatus.Status.LOADING -> {
                                   _binding!!.shimmerEffectCategory.isVisible(true, _binding!!.rvCategories)
                                }
                                DataStatus.Status.SUCCESS -> {

                                    Log.d(TAG, "Category " + it.data)

                                    _binding!!.shimmerEffectCategory.visibility = View.GONE
                                    _binding!!.rvCategories.visibility = View.VISIBLE
                                    _binding!!.shimmerEffectCategory.stopShimmer()

                                   Thread.sleep(1000L)
                                    _binding!!.shimmerEffectCategory.isVisible(false, _binding!!.rvCategories)
                                    it.data?.forEach { it1 ->
                                        adapter!!.onAddItems(it1)
                                    }


                                }
                                DataStatus.Status.ERROR -> {
                                    Thread.sleep(1000L)
                                    _binding!!.shimmerEffect.isVisible(false, _binding!!.rvCategories)
                                    _binding!!.shimmerEffect.stopShimmer()
                                    Log.d(TAG, "Category error " + it.message)
                                    Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }


        observeMain()
    }

    private fun observeMain(){

        productViewModel.productItem.observe(requireActivity(), Observer { info ->


            Thread.sleep(1000L)

            _binding!!.textViewProductName.text = info.data!!.title
            _binding!!.textViewProductRating.text = info.data!!.rating.toString()
            _binding!!.textViewProductPrice.text = info.data!!.price.toString() + " PHP"

            Glide
                .with(requireContext())
                .load(info.data.thumbnail)
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
        _binding!!.shimmerEffectCategory.startShimmer()
    }

    @SuppressLint("SuspiciousIndentation")
    private fun stopShimmer(){

        _binding!!.shimmerEffect.visibility = View.GONE
        _binding!!.linearContent.visibility = View.VISIBLE
        _binding!!.shimmerEffect.stopShimmer()


    }
}