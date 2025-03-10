package com.davemac.product_techexam.View.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.davemac.product_techexam.Factory.ProductFactory
import com.davemac.product_techexam.R
import com.davemac.product_techexam.Repository.ProductRepository
import com.davemac.product_techexam.Utils.Constant.TAG
import com.davemac.product_techexam.Utils.DataStatus
import com.davemac.product_techexam.Utils.RecyclerViewLoadMoreScroll
import com.davemac.product_techexam.Utils.isVisible
import com.davemac.product_techexam.Utils.setDivider
import com.davemac.product_techexam.View.Adapter.ProductAdapter
import com.davemac.product_techexam.ViewModel.ProductViewModel
import com.davemac.product_techexam.databinding.FragmentProductlistBinding
import kotlinx.coroutines.launch


class ProductListFragment : Fragment(){

    private var adapter: ProductAdapter?= null

    private var _binding: FragmentProductlistBinding?= null

    private val binding get() = _binding!!

    private lateinit var productsViewModel: ProductViewModel

    private var skip: Int = 0

    private var isLoadMore: Boolean =  false

    private var currentCount = 0

    private var productsTotal = 0

    private var limit: Int = 0



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProductlistBinding.inflate(inflater, container, false)

        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productRepository = ProductRepository(requireContext())
        val viewModelFactory  = ProductFactory(productRepository, requireActivity())

        productsViewModel = ViewModelProvider(this, viewModelFactory).get(ProductViewModel::class.java)

        val linearLayoutManager = LinearLayoutManager(requireContext())
        _binding!!.rvProductsList.layoutManager = linearLayoutManager

        if (adapter == null){
            adapter = ProductAdapter(requireContext())

        }
        _binding!!.rvProductsList.adapter = adapter

        _binding!!.rvProductsList.setDivider(R.drawable.recyclerview_divider)
        _binding!!.shimmerEffect.startShimmer()

        adapter!!.onItemClick = {
            Log.d(TAG, it.id.toString())

            val bundle = bundleOf(getString(R.string.productid) to it.id.toString())

            Navigation.findNavController(binding.root).navigate(R.id.action_productListFragment_to_productItemFragment, bundle)
        }

        lifecycleScope.launch {
            binding.apply {
                productsViewModel.loadProducts(skip)

                productsViewModel.productList.observe(viewLifecycleOwner){
                    when(it.status){
                        DataStatus.Status.LOADING -> {
                            _binding!!.shimmerEffect.isVisible(true, _binding!!.rvProductsList)
                        }
                        DataStatus.Status.SUCCESS -> {
                            productsTotal = it.data?.total ?: 0
                            limit = it.data?.limit ?: 0

                            Thread.sleep(1000L)

                            _binding!!.shimmerEffect.isVisible(false, _binding!!.rvProductsList)
                            _binding!!.shimmerEffect.stopShimmer()

                        }
                        DataStatus.Status.ERROR -> {
                            Thread.sleep(1000L)
                            _binding!!.shimmerEffect.isVisible(false, _binding!!.rvProductsList)
                            _binding!!.shimmerEffect.stopShimmer()
                            Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                productsViewModel.cacheProducts.observe(viewLifecycleOwner){
                    it.forEach { prod ->
                        adapter!!.onAddItems(prod)
                    }
                }
            }
        }

        val paginationScroll = object : RecyclerViewLoadMoreScroll(linearLayoutManager){
            override fun loadMoreItems(page: Int, totalItemsCount: Int) {

             if (isLoadMore){
                  return
             }

                currentCount += totalItemsCount

                if (limit != 0){
                    productsViewModel.loadProducts(currentCount)
                }

            }

        }

        _binding!!.rvProductsList.addOnScrollListener(paginationScroll)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}