package com.cvelez.freemarkettest.feactureArticleDetail.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import coil.compose.rememberAsyncImagePainter
import com.cvelez.freemarkettest.R
import com.cvelez.freemarkettest.feactureArticleDetail.data.model.ArticleDetail
import com.cvelez.freemarkettest.feactureArticleDetail.data.model.ArticlePictures
import com.cvelez.freemarkettest.feactureArticleDetail.presentation.estateUi.ArticleDetailUiState
import com.cvelez.freemarkettest.ui.Utils
import com.cvelez.freemarkettest.ui.theme.TestMeliTheme
import com.cvelez.freemarkettest.featureSearch.data.model.ArticleAttributes
@Composable
fun ArticleDetailScreen(
    uiState: ArticleDetailUiState,
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
    onBuyClicked: () -> Unit,
    onAddToCartClicked: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        contentAlignment = Alignment.Center,
    ) {
        if (uiState.loadingState) {
            CircularProgressIndicator(color = MaterialTheme.colors.primary)
        } else {
            val listState = rememberLazyListState()
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                TopBar(onBackClicked = onBackClicked)
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = listState
                ) {
                    item {
                        uiState.product?.pictures?.firstOrNull()?.let { picture ->
                            ZoomableImageCard(pictureUrl = picture.secure_url)
                        }
                    }

                    item {
                        uiState.product?.let { product ->
                            ProductInfoCard(
                                title = product.title ?: "",
                                price = product.price ?: 0L
                            )
                        }
                        ActionButtons(onBuyClicked = onBuyClicked, onAddToCartClicked = onAddToCartClicked)
                    }

                    item {
                        uiState.product?.attributes?.let { attributes ->
                            AttributesCard(attributes = attributes)
                        }
                    }

                    item {
                        uiState.product?.let { product ->
                            AdditionalInfoCard(
                                condition = product.condition ?: "",
                                permalink = product.permalink ?: ""
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TopBar(onBackClicked: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(onClick = onBackClicked) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colors.surface
            )
        }
    }
}

@Composable
fun ZoomableImageCard(pictureUrl: String?) {
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    Card(
        backgroundColor = (MaterialTheme.colors.background),
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp) // Ajuste la altura para no ocupar toda la pantalla en horizontal
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale *= zoom
                    offset = Offset(offset.x + pan.x, offset.y + pan.y)
                }
            }
            .graphicsLayer(
                scaleX = maxOf(1f, minOf(3f, scale)),
                scaleY = maxOf(1f, minOf(3f, scale)),
                translationX = offset.x,
                translationY = offset.y
            ),
        border = BorderStroke(1.dp, MaterialTheme.colors.background)
    ) {
        Image(
            painter = rememberAsyncImagePainter(pictureUrl),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun ProductInfoCard(title: String, price: Long) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.secondaryVariant
        )
        Text(
            text = Utils.formatPrice(price),
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.secondaryVariant
        )
    }
}

@Composable
fun AttributesCard(attributes: List<ArticleAttributes>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = stringResource(R.string.caracteriticas),
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.secondary
        )
        Spacer(modifier = Modifier.height(8.dp))
        attributes.forEach { attribute ->
            Text(
                text = "- ${attribute.name}: ${attribute.valueName}",
                modifier = Modifier.padding(start = 16.dp),
                color = MaterialTheme.colors.secondaryVariant
            )
        }
    }
}

@Composable
fun AdditionalInfoCard(condition: String, permalink: String) {
    Card(
        backgroundColor = (MaterialTheme.colors.background),
        modifier = Modifier.fillMaxWidth(),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(R.string.condicion, condition),
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.mas_informacion, permalink),
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Composable
fun ActionButtons(onBuyClicked: () -> Unit, onAddToCartClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onBuyClicked,
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant)
        ) {
            Text(text = "Comprar ahora", color = MaterialTheme.colors.secondaryVariant)
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onAddToCartClicked,
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onSecondary)
        ) {
            Text(text = "Agregar al carrito", color = MaterialTheme.colors.onBackground)
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {},
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
        ) {
            Text(text = "💳 !Paga en hasta 12 cuotas sin interés¡", color = MaterialTheme.colors.secondaryVariant)
        }
    }
}

@Preview
@Composable
fun ProductDetailScreenPreview() {
    TestMeliTheme {
        Surface {
            ArticleDetailScreen(
                uiState = ArticleDetailUiState(
                    loadingState = false,
                    product = ArticleDetail(
                        title = "Prueba",
                        price = 12000L,
                        pictures = listOf(
                            ArticlePictures(
                                id = "",
                                url = "",
                                secure_url = "",
                                size = "",
                                max_size = "",
                                quality = ""
                            )
                        ),
                        attributes = listOf(
                            ArticleAttributes(name = "Color", valueName = "Red"),
                            ArticleAttributes(name = "Size", valueName = "Medium")
                        ),
                        condition = "Nuevo",
                        permalink = "http://example.com"
                    )
                ),
                onBackClicked = {}, onBuyClicked = {}, onAddToCartClicked = {}
            )
        }
    }
}