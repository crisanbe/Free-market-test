package com.cvelez.freemarkettest.featureSearch.presentation

//noinspection UsingMaterialAndMaterial3Libraries
import android.content.Context
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.LocalImageLoader
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Scale
import coil.size.Size
import com.cvelez.freemarkettest.R
import com.cvelez.freemarkettest.featureSearch.data.model.Article
import com.cvelez.freemarkettest.featureSearch.data.model.Seller
import com.cvelez.freemarkettest.featureSearch.data.model.Shipping
import com.cvelez.freemarkettest.ui.Utils
import com.cvelez.freemarkettest.ui.theme.TestMeliTheme

@Composable
fun ArticleList(products: List<Article>, modifier: Modifier, onProductClick: (String?) -> Unit) {
    LazyColumn {
        items(products) { product ->
            ProductRow(product, modifier, onProductClick)
            HorizontalDivider(color = Color.LightGray)
        }
    }
}

@Composable
fun ProductRow(product: Article, modifier: Modifier, onProductClick: (String?) -> Unit) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        onClick = { onProductClick(product.id) },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colors.background,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            ImageDecodedLocal(
                modifier = Modifier
                    .size(100.dp),
                urlImage = product.thumbnail,
                scaleImage = ContentScale.FillWidth,
                descriptionImage = "Imagen del producto"
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Text(
                    text = product.title?.trim().toString(),
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 2,
                    color = MaterialTheme.colors.secondaryVariant,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = Utils.formatPrice((product.price) ?: 0L),
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.secondaryVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = product.seller?.nickname ?: "",
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.secondaryVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = product.shipping?.tags?.firstOrNull() ?: "Envio gratis",
                    style = MaterialTheme.typography.button,
                    color = MaterialTheme.colors.secondary,
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
                    shipping = Shipping(tags = listOf("free_shipping")),
                    thumbnail = "https://tienda.movistar.com.co/media/catalog/product/cache/ebd1de6550e0d0b8d8c505e2a09b56c4/m/o/motog52azul01_1.jpg")
            ),modifier = Modifier, onProductClick = {} )
    }
}

@Composable
fun ImageDecodedLocal(
    modifier: Modifier,
    urlImage: String? = null,
    colorFilter: ColorFilter? = null,
    scaleImage: ContentScale,
    descriptionImage: String
) {
     if (!urlImage.isNullOrEmpty()) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                    .data(urlImage)
                    .scale(Scale.FILL)
                    .crossfade(true)
                    .size(Size.ORIGINAL)
                    .build(),
            placeholder =  painterResource(id = R.drawable.picture),
            contentDescription = descriptionImage,
            modifier = modifier
                .clip(RoundedCornerShape(12.dp))
                .border(2.dp, MaterialTheme.colors.background, RoundedCornerShape(12.dp))
                .background(Color.LightGray, RoundedCornerShape(12.dp)),
            contentScale = scaleImage
        )
    }
}

@Composable
fun CustomLoad(
    context: Context,
    stateProgress: Boolean,
    color: Color = MaterialTheme.colors.background
) {
    if (stateProgress) {
        val isDarkTheme = isSystemInDarkTheme()
        val imageRes = if (isDarkTheme) R.drawable.mercadolibre else R.drawable.dark_mercadolibre

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color),
            contentAlignment = Alignment.Center
        ) {
            val imgLoader = ImageLoader.Builder(context).components {
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }.build()

            Image(
                painter = rememberAsyncImagePainter(
                    model = imageRes,
                    imageLoader = imgLoader
                ),
                contentDescription = null,
                modifier = Modifier.size(170.dp)
            )
        }
    }
}

