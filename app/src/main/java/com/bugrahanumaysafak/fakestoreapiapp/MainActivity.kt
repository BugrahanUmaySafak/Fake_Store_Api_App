package com.bugrahanumaysafak.fakestoreapiapp

import ProductsPage
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bugrahanumaysafak.fakestoreapiapp.entity.ProductsResponseItem
import com.bugrahanumaysafak.fakestoreapiapp.viewmodel.ProductsPageViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        setContent {
            MainNavigation()
        }
    }
}

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "productsPage") {
        composable("productsPage") { ProductsPage(navController) }
        composable("addProductsPage") { AddProductsPage(navController) }
    }
}

