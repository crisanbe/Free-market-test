package com.cvelez.freemarkettest.featureSearch.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cvelez.freemarkettest.R
import com.cvelez.freemarkettest.featureSearch.presentation.viewModel.SearchItemUiState
import com.cvelez.freemarkettest.ui.theme.TestMeliTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchArticleScreen(
        uiState: SearchItemUiState,
        onSearchProduct: () -> Unit,
        onQuerySearch: (String) -> Unit,
        onProductClick: (String?) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    if (uiState.errorState) {
        LaunchedEffect(uiState.errorMessage != null) {
            snackbarHostState.showSnackbar(uiState.errorMessage.toString())
        }
    }

    Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                TopAppBar(
                    title = {
                        SearchBarWithIconsOutside(
                            searchText = uiState.searchQuery,
                            modifier = Modifier.fillMaxWidth(),
                            onSearchTextChanged = { onQuerySearch(it) },
                            onSearchProduct = { onSearchProduct() },
                            onLeftIconClick = { /* Acción del ícono izquierdo */ },
                            onRightIconClick = { /* Acción del ícono derecho */ },
                            label = "Ofertas de la semana"
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = androidx.compose.material.MaterialTheme.colors.primary,
                        titleContentColor = androidx.compose.material.MaterialTheme.colors.onSurface
                    )
                )
            },
            containerColor = androidx.compose.material.MaterialTheme.colors.onBackground,
            contentColor = androidx.compose.material.MaterialTheme.colors.onBackground
        ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            if (uiState.loadingState) {
                CircularProgressIndicator(
                    color = androidx.compose.material.MaterialTheme.colors.primaryVariant
                )
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    ArticleList(uiState.productList, onProductClick = onProductClick)
                }
            }
        }
    }
}

@Composable
fun SearchBarWithIconsOutside(
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    onSearchProduct: () -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Buscar",
    onLeftIconClick: () -> Unit,
    onRightIconClick: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    var isHintDisplayed by remember { mutableStateOf(searchText.isEmpty()) }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy((-30).dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        IconButton(
            onClick = onLeftIconClick,
            modifier = Modifier
                .size(50.dp)
                .offset(x = (-20).dp) // Ajustar el margen izquierdo
        ) {
            Icon(
                imageVector = Icons.Outlined.Menu,
                contentDescription = null,
                tint = Color.DarkGray
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .height(45.dp)
                .padding(horizontal = 10.dp)
                .background(Color.White, CircleShape)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp, vertical = 10.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color.Gray
                )

                Box(modifier = Modifier.fillMaxWidth()) {
                    // Mostrar el placeholder solo si no hay texto
                    if (isHintDisplayed) {
                        Text(
                            text = label,
                            style = TextStyle(color = Color.Gray),
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .clickable {
                                    // Al hacer clic en el texto de placeholder, mostrar el teclado
                                    isHintDisplayed = false
                                    focusRequester.requestFocus()
                                }
                        )
                    }

                    BasicTextField(
                        value = searchText,
                        onValueChange = {
                            onSearchTextChanged(it)
                            isHintDisplayed =
                                it.isEmpty() // Mostrar el placeholder si el texto está vacío
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Search
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                onSearchProduct()
                                keyboardController?.hide()
                            },
                        ),
                        maxLines = 1,
                        singleLine = true,
                        textStyle = TextStyle(color = Color.Black),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp)
                            .focusRequester(focusRequester)
                            .onFocusChanged { focusState ->
                                // Ocultar el placeholder cuando el campo de texto está enfocado
                                isHintDisplayed = focusState.isFocused && searchText.isEmpty()
                            }
                    )
                }
            }
        }

        IconButton(
            onClick = onRightIconClick,
            modifier = Modifier
                .size(50.dp)
                .offset(x = 18.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.ShoppingCart,
                contentDescription = null,
                tint = Color.DarkGray
            )
        }
    }
}

@Preview(
    device = Devices.PHONE,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_MASK,
    showSystemUi = true
)
@Composable
fun SearchProductScreenPreview() {
    TestMeliTheme {
        Surface {
            SearchArticleScreen(
                uiState = SearchItemUiState(),
                onSearchProduct = {},
                onQuerySearch = {}, onProductClick = {})
        }
    }
}