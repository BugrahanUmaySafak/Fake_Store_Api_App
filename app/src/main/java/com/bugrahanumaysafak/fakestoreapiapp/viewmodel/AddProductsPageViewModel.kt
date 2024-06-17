package com.bugrahanumaysafak.fakestoreapiapp.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bugrahanumaysafak.fakestoreapiapp.entity.NetworkResponse
import com.bugrahanumaysafak.fakestoreapiapp.entity.ProductsResponseItem
import com.bugrahanumaysafak.fakestoreapiapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch

class AddProductsPageViewModel : ViewModel() {
    private val productApi = RetrofitInstance.productsApi

    private val _productResult = MutableLiveData<NetworkResponse<ProductsResponseItem>>()
    val productResult: LiveData<NetworkResponse<ProductsResponseItem>> = _productResult

    fun addProduct(product: ProductsResponseItem) {
        viewModelScope.launch {
            _productResult.value = NetworkResponse.Loading
            try {
                val response = productApi.addProducts(product)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _productResult.value = NetworkResponse.Success(it)
                    } ?: run {
                        _productResult.value = NetworkResponse.Error("Unexpected error occurred.")
                    }
                } else {
                    _productResult.value = NetworkResponse.Error("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                _productResult.value = NetworkResponse.Error("Exception: ${e.message}")
            }
        }
    }
}


