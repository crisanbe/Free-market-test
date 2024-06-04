package com.cvelez.freemarkettest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cvelez.freemarkettest.feactureArticleDetail.presentation.ArticleDetailRoute
import com.cvelez.freemarkettest.feactureArticleDetail.presentation.viewModel.ArticleDetailViewModel
import com.cvelez.freemarkettest.featureSearch.presentation.CustomLoad
import com.cvelez.freemarkettest.featureSearch.presentation.SearchArticleRoute
import com.cvelez.freemarkettest.featureSearch.presentation.viewModel.SearchArticleViewModel
import com.cvelez.freemarkettest.ui.navigation.Route.HOME
import com.cvelez.freemarkettest.ui.navigation.Route.PRODUCT_DETAIL
import com.cvelez.freemarkettest.ui.theme.TestMeliTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestMeliTheme {
                val navController = rememberNavController()
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    NavigationHost(navController)
                }
            }
        }
    }

    @Composable
    fun NavigationHost(navController: NavHostController) {
        val searchViewModel = hiltViewModel<SearchArticleViewModel>()

        if (searchViewModel.uiState.loadingState) {
            CustomLoad(context = this@MainActivity, stateProgress = true)
        } else {
            NavHost(navController = navController, startDestination = HOME) {
                composable(route = HOME) {
                    SearchScreen(navController)
                }
                composable(
                    route = "${PRODUCT_DETAIL}/{productId}",
                    arguments = listOf(navArgument("productId") { type = NavType.StringType })
                ) {
                    val detailViewModel = hiltViewModel<ArticleDetailViewModel>()
                    ArticleDetailRoute(viewModel = detailViewModel, onBackClicked = {
                        navController.navigateUp()
                    })
                }
            }
        }
    }

    @Composable
    fun SearchScreen(navController: NavHostController) {
        val searchViewModel = hiltViewModel<SearchArticleViewModel>()
        SearchArticleRoute(
            viewModel = searchViewModel,
            modifier = Modifier,
            onShowProductDetail = { productId ->
                productId?.let {
                    navController.navigate("${PRODUCT_DETAIL}/$it")
                }
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TestMeliTheme {
    }
}
