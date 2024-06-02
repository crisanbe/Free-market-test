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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cvelez.freemarkettest.feactureItemDetail.presentation.ArticleDetailRoute
import com.cvelez.freemarkettest.feactureItemDetail.presentation.ArticleDetailViewModel
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
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()

                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    NavHost(navController = navController, startDestination = HOME) {
                        composable(HOME) {
                            val viewModel = hiltViewModel<SearchArticleViewModel>()
                            SearchArticleRoute(viewModel = viewModel, onShowProductDetail = {
                                it?.let {
                                    navController.navigate("${PRODUCT_DETAIL}/$it")
                                }
                            })
                        }
                        composable("${PRODUCT_DETAIL}/{productId}"){
                            val viewModel = hiltViewModel<ArticleDetailViewModel>()
                            ArticleDetailRoute(viewModel = viewModel, onBackClicked = {
                                navController.navigateUp()
                            })
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TestMeliTheme {
    }
}