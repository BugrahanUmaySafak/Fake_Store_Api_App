package com.bugrahanumaysafak.fakestoreapiapp

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bugrahanumaysafak.fakestoreapiapp.entity.NetworkResponse
import com.bugrahanumaysafak.fakestoreapiapp.entity.ProductsResponseItem
import com.bugrahanumaysafak.fakestoreapiapp.entity.Rating
import com.bugrahanumaysafak.fakestoreapiapp.viewmodel.AddProductsPageViewModel

@Composable
fun AddProductsPage(navController: NavController) {
    val viewModel: AddProductsPageViewModel = viewModel()

    var title by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var rate by remember { mutableStateOf("") }
    var count by remember { mutableStateOf("") }

    val addProductResult by viewModel.productResult.observeAsState()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Price") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = imageUrl,
            onValueChange = { imageUrl = it },
            label = { Text("Image URL") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = category,
            onValueChange = { category = it },
            label = { Text("Category") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = rate,
            onValueChange = { rate = it },
            label = { Text("Rate") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = count,
            onValueChange = { count = it },
            label = { Text("Count") }
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = {
            val product = ProductsResponseItem(
                title = title,
                price = price.toDoubleOrNull() ?: 0.0,
                description = description,
                image = imageUrl,
                category = category,
                rating = Rating(
                    rate = rate.toDoubleOrNull() ?: 0.0,
                    count = count.toIntOrNull() ?: 0
                )


            )
            viewModel.addProduct(product)
        }) {
            Text("Add Product")
        }

        when (val result = addProductResult) {
            is NetworkResponse.Error -> {
                Text(text = result.message)
            }
            NetworkResponse.Loading -> {
                CircularProgressIndicator()
            }
            is NetworkResponse.Success -> {
                Text(text = "Product added successfully")
                navController.navigate("productsPage") {
                    popUpTo("addProductsPage") { inclusive = true }
                }
            }
            else -> Log.e("TAG", "AddProductsPage")
        }
    }
}




