package com.cvelez.freemarkettest.feactureItemDetail.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.cvelez.freemarkettest.R
import com.cvelez.freemarkettest.feactureItemDetail.data.model.ArticleDetail
import com.cvelez.freemarkettest.feactureItemDetail.data.model.ArticlePictures
import com.cvelez.freemarkettest.ui.Utils
import com.cvelez.freemarkettest.ui.theme.TestMeliTheme

@Composable
fun ArticleDetailScreen(uiState: ProductDetailUiState, modifier: Modifier = Modifier, onBackClicked: () -> Unit) {
    Column(modifier = modifier.fillMaxSize()) {
        if (uiState.loadingState) {
            LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    color = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }

        IconButton(onClick = onBackClicked) {
            Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "Back")
        }


        LazyColumn(
                modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
        ) {
            uiState.product?.pictures?.let { list ->
                items(list) {

                }
            }
            item {
                if ((uiState.product?.pictures?.size ?: 0) > 1) {
                    Image(
                            painter = rememberAsyncImagePainter(uiState.product?.pictures?.get(0)?.secure_url),
                            contentDescription = null,
                            modifier = Modifier
                                    .fillMaxWidth()
                                    .height(400.dp),
                            contentScale = ContentScale.FillWidth
                    )
                }
            }


            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                // Product Name
                Text(
                        text = uiState.product?.title ?: "",
                        style = MaterialTheme.typography.titleLarge
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                // Product Price
                Text(
                        text = Utils.formatPrice((uiState.product?.price) ?: 0L),
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                // Product Attributes
                Text(
                        text = stringResource(R.string.caracteriticas),
                        color = Color.Gray
                )
                uiState.product?.attributes?.forEach { attribute ->
                    Text(
                            text = "- ${attribute.name}: ${attribute.valueName}",

                            modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                // Product Condition
                Text(
                        text = stringResource(R.string.condicion, uiState.product?.condition ?: ""),
                        //style = MaterialTheme.typography.body1
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                // Product Seller
                Text(
                        text = stringResource(R.string.mas_informacion, uiState.product?.permalink
                                ?: ""),
                )
            }
        }
    }

}

@Preview
@Composable
fun ProductDetailScreenPreview() {
    TestMeliTheme {
        Surface {
            ArticleDetailScreen(uiState = ProductDetailUiState(
                    loadingState = false,
                    ArticleDetail(title = "Prueba",
                            price = 12000L, pictures = listOf(ArticlePictures(
                            id = "",
                            url = "",
                            secure_url = "",
                            size = "",
                            max_size = "",
                            quality = ""
                    )))
            ), onBackClicked = {})
        }
    }
}