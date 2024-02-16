package com.jacky.cryptocrazy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jacky.cryptocrazy.ui.theme.CryptoCrazyTheme
import com.jacky.cryptocrazy.view.CryptoDetailScreen
import com.jacky.cryptocrazy.view.CryptoListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptoCrazyTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "crypto_list_screen") {
                    composable("crypto_list_screen") {
                        CryptoListScreen(navController = navController)
                    }
                    composable("crypto_detail_screen/{cryptoName}/{cryptoPrice}",
                        arguments = listOf(
                            navArgument("cryptoName") {
                                type = NavType.StringType
                            },
                            navArgument("cryptoPrice") {
                                type = NavType.StringType
                            }
                        )) {
                        val cryptoName = remember {
                            it.arguments?.getString("cryptoName")
                        }

                        val cryptoPrice = remember {
                            it.arguments?.getString("cryptoPrice")
                        }

                        CryptoDetailScreen(
                            name = cryptoName ?: "BTC",
                            price = cryptoPrice ?: "0.00",
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}

