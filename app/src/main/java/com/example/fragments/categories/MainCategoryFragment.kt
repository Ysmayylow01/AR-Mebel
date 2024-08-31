package com.example.fragments.categories

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adapters.BestDealsAdapter
import com.example.adapters.BestProductsAdapter
import com.example.adapters.SpecialProductsAdapter
import com.example.aroom.R
import com.example.aroom.databinding.FragmentMainCategoryBinding
import com.example.data.CartProduct
import com.example.data.Product
import com.example.util.Resource
import com.example.util.showBottomNavigationView
import com.example.viewmodel.shopping.DetailsViewModel
import com.example.viewmodel.shopping.MainCategoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private val TAG = "MainCategoryFragment"
@AndroidEntryPoint
class MainCategoryFragment : Fragment(R.layout.fragment_main_category) {
    private lateinit var binding : FragmentMainCategoryBinding
    private lateinit var specialProductAdapter : SpecialProductsAdapter
    private lateinit var bestDealsProductAdapter: BestDealsAdapter
    private lateinit var bestProductAdapter : BestProductsAdapter
    private val viewModel by viewModels<MainCategoryViewModel>()
    private val cartViewModel by viewModels<DetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainCategoryBinding.inflate(inflater)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpecialProductRv()
        setupBestDealsProductRv()
        setupBestProductRv()

        binding.swipeRefreshLayout.setOnRefreshListener {
         /*   viewModel.fetchSpecialProducts()
            viewModel.fetchBestDealsProducts()
            viewModel.fetchBestProducts()*/
            lifecycleScope.launch {
                delay(1000)
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }

        specialProductAdapter.onClick = {
            val b = Bundle().apply { putParcelable("product" , it) }
            findNavController().navigate(R.id.action_homeFragment_to_productDetailsFragment,b)
        }
        specialProductAdapter.onBtnClick = {

            val b = Bundle().apply { putParcelable("product" , it) }
            cartViewModel.addUpdateProductInCart(CartProduct(it,1))
            Toast.makeText(requireContext(),"Added to cart",Toast.LENGTH_SHORT).show()
        }

        bestProductAdapter.onClick = {
            val b = Bundle().apply { putParcelable("product" , it) }
            findNavController().navigate(R.id.action_homeFragment_to_productDetailsFragment,b)
        }


        bestDealsProductAdapter.onBtnClick ={
            val b = Bundle().apply { putParcelable("product",it) }
            findNavController().navigate(R.id.action_homeFragment_to_productDetailsFragment,b)
        }

        bestDealsProductAdapter.onClick = {
            val b = Bundle().apply { putParcelable("product" , it) }
            findNavController().navigate(R.id.action_homeFragment_to_productDetailsFragment,b)
        }
        val product  = mutableListOf<Product>()
        product.add(Product("1", "Kreslo", "models/arm_chair__furniture.glb", "Chair", 5.667f, 7.6f,"Rysgal Mebel", listOf(Color.GRAY, Color.GREEN), listOf("40", "50"), listOf("drawable/kreslo")))
        product.add(Product("2", "Kreslo gülgüne", "models/pink.glb", "best", 2000f, 0.2f,"Taze gelen haryt", listOf(Color.YELLOW, Color.RED, Color.BLACK), listOf("65", "34"), listOf("drawable/kreslo_gulgune")))
        product.add(Product("3", "Aşhana stol stul", "models/ashana_stul.glb", "Chair", 50.2f, 0.1f,"Aşhanada goýulýan iýip içmek üçin stol stullary", listOf(Color.WHITE, Color.CYAN), listOf("50", "60"), listOf("drawable/ashana_stul")))


        specialProductAdapter.differ.submitList(product)

       /* lifecycleScope.launchWhenStarted {
            viewModel.specialProducts.collectLatest {
                when(it){
                    is Resource.Loading -> {
                        showLoading()
                    }
                    is Resource.Success -> {
                        specialProductAdapter.differ.submitList(product)
                        hideLoading()
                        binding.swipeRefreshLayout.isRefreshing = false
                    }
                    is Resource.Error -> {
                        hideLoading()
                        Log.e(TAG,it.message.toString())
                        Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()

                    }
                    else -> Unit
                }
            }
        }*/
        val product1  = mutableListOf<Product>()
        product1.add(Product("3", "Aşhana stol stul", "models/ashana_stul.glb", "Chair", 50.2f, 0.1f,"Aşhanada goýulýan iýip içmek üçin stol stullary", listOf(Color.WHITE, Color.CYAN), listOf("50", "60"), listOf("drawable/ashana_stul")))
        product1.add(Product("1", "Gaz plita", "models/plita.glb", "Chair", 3.667f, 0.1f,"6 sany gözi we duhowkasy bar", listOf(Color.BLUE, Color.BLACK), listOf("50", "60"), listOf("drawable/plita")))

        bestDealsProductAdapter.differ.submitList(product1)



    /*    lifecycleScope.launchWhenStarted {
            viewModel.bestProducts.collectLatest {
                when(it){
                    is Resource.Loading -> {
                        binding.bestProductsProgressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        bestProductAdapter.differ.submitList(it.data)
                        binding.bestProductsProgressBar.visibility = View.GONE
                        binding.swipeRefreshLayout.isRefreshing = false
                    }
                    is Resource.Error -> {

                        Log.e(TAG,it.message.toString())
                        Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                        binding.bestProductsProgressBar.visibility = View.GONE

                    }
                    else -> Unit
                }
            }
        }*/
/*

        lifecycleScope.launchWhenStarted {
            viewModel.bestDealsProducts.collectLatest {
                when(it){
                    is Resource.Loading -> {
                        showLoading()
                    }
                    is Resource.Success -> {
                        Log.d("qwer", "onViewCreated: ${it.data}")
                        bestDealsProductAdapter.differ.submitList(it.data)
                        hideLoading()
                        binding.swipeRefreshLayout.isRefreshing = false
                    }
                    is Resource.Error -> {
                        hideLoading()
                        Log.e(TAG,it.message.toString())
                        Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()

                    }
                    else -> Unit
                }
            }
        }*/
        val product2  = mutableListOf<Product>()
        product2.add(Product("1", "Gaz plita", "models/plita.glb", "Chair", 3.667f, 0.1f,"6 sany gözi we duhowkasy bar", listOf(Color.BLUE, Color.BLACK), listOf("50", "60"), listOf("drawable/plita")))
        product2.add(Product("2", "Şkaf", "models/skaf.glb", "Chair", 6.667f, 0.6f,"Rysgal Mebel", listOf(Color.RED, Color.GREEN), listOf("12", "13"), listOf("drawable/skaf1", "skaf2")))
        product2.add(Product("3", "Diwan", "models/sofa.glb", "Chair", 5.667f, 0.1f,"Oturylýan diwan", listOf(Color.RED, Color.GREEN), listOf("12", "13"), listOf("drawable/diwan1", "drawable/diwan2")))
        product2.add(Product("4", "Parta aýnaly", "models/partaayna.glb", "Chair", 5600f, 0.1f,"Zat goýulýan parta", listOf(Color.RED, Color.GREEN), listOf("12", "13"), listOf("drawable/partaayna", )))
        product2.add(Product("5", "Parta", "models/table.glb", "Chair", 400.67f, 0.1f,"Zat goýulýan parta", listOf(Color.RED, Color.GREEN), listOf("12", "13"), listOf("drawable/parta", )))
        product2.add(Product("6", "Kompýuter stul", "models/oturgych.glb", "Chair", 300.7f, 0.1f,"Täze gelen önümler", listOf(Color.BLACK, Color.WHITE), listOf("12", "13"), listOf("drawable/stul1", "drawable/stul2" )))


        bestProductAdapter.differ.submitList(product2)

//        binding.nestedScrollMainCategory.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener{ v,_,scrollY,_,_ ->
//            if(v.getChildAt(0).bottom <= v.height + scrollY){
//                viewModel.fetchBestProducts()
//            }
//
//        })
    }

    private fun setupBestProductRv() {
        bestProductAdapter = BestProductsAdapter()
        binding.rvBestProducts.apply {
            layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false   )
            adapter = bestProductAdapter
        }
    }

    private fun setupBestDealsProductRv() {
        bestDealsProductAdapter = BestDealsAdapter()
        binding.rvBestDealsProducts.apply {
            layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.HORIZONTAL,false)
            adapter = bestDealsProductAdapter
        }
    }

    private fun showLoading() {
        binding.mainCategoryProgressBar.visibility = View.VISIBLE
    }
    private fun hideLoading() {
        binding.mainCategoryProgressBar.visibility = View.GONE
    }

    private fun setupSpecialProductRv() {
        specialProductAdapter = SpecialProductsAdapter()
        binding.rvSpecialProducts.apply {
            layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.HORIZONTAL,false)
            adapter = specialProductAdapter
        }
    }

    override fun onResume() {
        super.onResume()

        showBottomNavigationView()
    }
}