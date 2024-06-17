import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.bugrahanumaysafak.fakestoreapiapp.R
import com.bugrahanumaysafak.fakestoreapiapp.entity.NetworkResponse
import com.bugrahanumaysafak.fakestoreapiapp.viewmodel.ProductsPageViewModel

@Composable
fun ProductsPage(navController: NavHostController) {
    val viewModel: ProductsPageViewModel = viewModel()
    val productResult = viewModel.productsResult.observeAsState()
    val deleteResult = viewModel.deleteResult.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.getData()
    }

    LaunchedEffect(deleteResult.value) {
        when (deleteResult.value) {
            is NetworkResponse.Loading -> {
                Log.d("ProductsPage", "Deleting product...")
            }
            is NetworkResponse.Success -> {
                Log.d("ProductsPage", "Product deleted successfully")
            }
            is NetworkResponse.Error -> {
                (deleteResult.value as NetworkResponse.Error).message
            }
            else -> {
                Log.d("ProductsPage", "Awaiting action")
            }
        }
    }

    when (val result = productResult.value) {
        is NetworkResponse.Error -> {
            Text(text = result.message)
        }
        NetworkResponse.Loading -> {
            CircularProgressIndicator()
        }
        is NetworkResponse.Success -> {

            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Button(
                    onClick = {
                        navController.navigate("addProductsPage")
                        Log.e("TAG", result.data.toString())
                    },
                    modifier = Modifier
                        .padding(5.dp)
                        .size(width = 150.dp, height = 50.dp)
                ) {
                    Text(text = "Add Product")
                }
            }

            LazyColumn(
                modifier = Modifier.padding(top = 50.dp)
            ) {
                items(
                    count = result.data.size,
                    itemContent = {
                        val product = result.data[it]
                        Card(
                            modifier = Modifier
                                .padding(all = 10.dp)
                                .fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = colorResource(id = R.color.mainColor)
                            ),
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text(
                                        text = "${product.title}",
                                        textAlign = TextAlign.Start,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = colorResource(id = R.color.mainTextColor),
                                        modifier = Modifier.weight(1f)
                                    )

                                    Text(
                                        text = "${product.category}",
                                        textAlign = TextAlign.End,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = colorResource(id = R.color.mainTextColor),
                                        modifier = Modifier.weight(1f)
                                    )
                                }

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    AsyncImage(
                                        model = "${product.image}",
                                        contentDescription = "Product Image",
                                        modifier = Modifier
                                            .size(128.dp)
                                            .weight(1f)
                                    )

                                    Text(
                                        text = "${product.description}",
                                        textAlign = TextAlign.Start,
                                        fontSize = 10.sp,
                                        color = colorResource(id = R.color.mainTextColor),
                                        modifier = Modifier
                                            .padding(start = 16.dp)
                                            .weight(2f)
                                    )
                                }

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(
                                        verticalArrangement = Arrangement.SpaceBetween,
                                        horizontalAlignment = Alignment.Start,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            text = "Rate: ${product.rating?.rate}",
                                            textAlign = TextAlign.Start,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold,
                                        )

                                        Spacer(modifier = Modifier.height(4.dp))

                                        Text(
                                            text = "Count: ${product.rating?.count}",
                                            textAlign = TextAlign.Start,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold,
                                        )
                                    }

                                    Column(
                                        verticalArrangement = Arrangement.SpaceBetween,
                                        horizontalAlignment = Alignment.End,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            text = "Price: $${product.price}",
                                            textAlign = TextAlign.End,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                        )

                                        Spacer(modifier = Modifier.height(4.dp))

                                        IconButton(
                                            onClick = {
                                                viewModel.deleteProduct(product.id!!)
                                                Log.e("deleted", product.id.toString() )
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Delete,
                                                contentDescription = "Delete Icon",
                                                tint = colorResource(id = R.color.mainTextColor)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                )
            }
        }
        null -> Log.e("ProductsPage", "ProductsPage")
    }
}



