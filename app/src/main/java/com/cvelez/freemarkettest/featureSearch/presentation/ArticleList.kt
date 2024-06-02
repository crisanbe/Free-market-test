package com.cvelez.freemarkettest.featureSearch.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.cvelez.freemarkettest.featureSearch.data.model.Article
import com.cvelez.freemarkettest.featureSearch.data.model.Seller
import com.cvelez.freemarkettest.ui.Utils
import com.cvelez.freemarkettest.ui.theme.TestMeliTheme

@Composable
fun ArticleList(
    products: List<Article>,
    modifier: Modifier = Modifier,
    onProductClick: (String?) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(top = 0.dp, start = 0.dp, end = 0.dp, bottom = 0.dp),
    ) {
        items(products) { product ->
            ProductRow(productRecord = product, onProductClick = {
                onProductClick(product.id)
            })
            HorizontalDivider(color = Color.LightGray)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductRow(productRecord: Article, modifier: Modifier = Modifier, onProductClick: () -> Unit) {
    val formattedPrice = remember { Utils.formatPrice((productRecord.price) ?: 0L) }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = androidx.compose.material.MaterialTheme.colors.background,
        ),
        shape = RectangleShape,
        onClick = onProductClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier.size(100.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = androidx.compose.material.MaterialTheme.colors.background,
                ),
            ) {
                Image(
                    painter = rememberAsyncImagePainter(productRecord.thumbnail.toString()),
                    contentDescription = "Product Image",
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.LightGray),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = productRecord.title?.trim().toString(),
                    style = androidx.compose.material.MaterialTheme.typography.subtitle1,
                    maxLines = 2,
                    color = androidx.compose.material.MaterialTheme.colors.secondaryVariant,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = formattedPrice,
                    style = androidx.compose.material.MaterialTheme.typography.h6,
                    color = androidx.compose.material.MaterialTheme.colors.secondaryVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = productRecord.seller?.nickname ?: "",
                    style = androidx.compose.material.MaterialTheme.typography.subtitle1,
                    color = androidx.compose.material.MaterialTheme.colors.secondaryVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = productRecord.shipping?.tags?.get(0) ?: "",
                    style = androidx.compose.material.MaterialTheme.typography.button,
                    color = androidx.compose.material.MaterialTheme.colors.secondary,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun ProductListPreview() {
    TestMeliTheme {
        ArticleList(
            products = listOf(
                Article(
                    title = "celular",
                    price = 12000,
                    seller = Seller(nickname = "seller"),
                    shipping = com.cvelez.freemarkettest.featureSearch.data.model.Shipping(tags = listOf("free_shipping")),
                    thumbnail = "https://tienda.movistar.com.co/media/catalog/product/cache/ebd1de6550e0d0b8d8c505e2a09b56c4/m/o/motog52azul01_1.jpg")
            )) {

        }
    }
}
