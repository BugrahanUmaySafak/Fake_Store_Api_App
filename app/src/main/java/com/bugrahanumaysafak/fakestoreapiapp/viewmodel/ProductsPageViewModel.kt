package com.bugrahanumaysafak.fakestoreapiapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bugrahanumaysafak.fakestoreapiapp.entity.NetworkResponse
import com.bugrahanumaysafak.fakestoreapiapp.entity.ProductsResponseItem
import com.bugrahanumaysafak.fakestoreapiapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch

class ProductsPageViewModel : ViewModel() {
    private val productApi = RetrofitInstance.productsApi

    private val _productsResult = MutableLiveData<NetworkResponse<List<ProductsResponseItem>>>()
    val productsResult: LiveData<NetworkResponse<List<ProductsResponseItem>>> = _productsResult

    private val _deleteResult = MutableLiveData<NetworkResponse<Unit>>()
    val deleteResult: LiveData<NetworkResponse<Unit>> = _deleteResult

    fun getData() {
        _productsResult.value = NetworkResponse.Loading

        viewModelScope.launch {
            try {
                val response = productApi.getProducts()
                if (response.isSuccessful) {
                    response.body()?.let {
                        _productsResult.value = NetworkResponse.Success(it)
                    }
                } else {
                    _productsResult.value = NetworkResponse.Error("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                _productsResult.value = NetworkResponse.Error("Exception: ${e.message}")
            }
        }
    }

    fun deleteProduct(id: Int) {
        viewModelScope.launch {
            _deleteResult.value = NetworkResponse.Loading
            try {
                val response = productApi.deleteProducts(id)
                if (response.isSuccessful) {
                    _deleteResult.value = NetworkResponse.Success(Unit)
                    getData()
                } else {
                    _deleteResult.value = NetworkResponse.Error("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                _deleteResult.value = NetworkResponse.Error("Exception: ${e.message}")
            }
        }
    }
}
