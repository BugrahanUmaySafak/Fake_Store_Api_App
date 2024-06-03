package com.bugrahanumaysafak.fakestoreapiapp

import ProductsPage
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.bugrahanumaysafak.fakestoreapiapp.viewmodel.ProductsPageViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val productViewModel = ViewModelProvider(this)[ProductsPageViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            ProductsPage(productViewModel)
        }
    }
}

