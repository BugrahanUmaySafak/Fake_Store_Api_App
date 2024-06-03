
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bugrahanumaysafak.fakestoreapiapp.entity.NetworkResponse
import com.bugrahanumaysafak.fakestoreapiapp.entity.ProductsResponseItem
import com.bugrahanumaysafak.fakestoreapiapp.viewmodel.ProductsPageViewModel
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.bugrahanumaysafak.fakestoreapiapp.R

@Composable
fun ProductsPage(viewModel: ProductsPageViewModel) {

    val productResult = viewModel.productsResult.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.getData()
    }

    when (val result = productResult.value) {
        is NetworkResponse.Error -> {
            Text(text = result.message)
        }
        NetworkResponse.Loading -> {
            CircularProgressIndicator()
        }
        is NetworkResponse.Success -> {
            ProductList(products = result.data)
        }
        null -> Log.e("ProductsPage", "ProductsPage")
    }

}

@Composable
fun ProductList(products: List<ProductsResponseItem?>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(top = 40.dp)
    ) {
        items(
            items = products,
            key = { product -> product?.id ?: 0 }
        ) { product ->
            product?.let {
                ProductCard(data = it)
            }
        }
    }
}

@Composable
fun ProductCard(data: ProductsResponseItem) {
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
                .fillMaxSize()
                .height(300.dp)
                .clickable {
                    Log.e("TAG", "${data.title}")
                },
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                Text(
                    text = "${data.title}",
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.mainTextColor)
                )

                Spacer(modifier = Modifier.height(16.dp))

                AsyncImage(
                    model = data.image,
                    contentDescription = "Product Image",
                    modifier = Modifier
                        .size(128.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "${data.category}",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 10.sp,
                    color = colorResource(id = R.color.mainTextColor)
                )
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Rate: ${data.rating?.rate}",
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Count: ${data.rating?.count}",
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
    }

}



