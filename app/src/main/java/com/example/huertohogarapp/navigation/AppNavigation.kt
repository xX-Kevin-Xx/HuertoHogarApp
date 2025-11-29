package com.example.huertohogarapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.huertohogarapp.ui.screens.HomeScreen
import com.example.huertohogarapp.ui.screens.WelcomeScreen
import com.example.huertohogarapp.ui.screens.auth.LoginScreen
import com.example.huertohogarapp.ui.screens.auth.RegisterScreen
import com.example.huertohogarapp.ui.screens.WelcomeScreen
import com.example.huertohogarapp.ui.screens.products.ProductListScreen
import com.example.huertohogarapp.ui.screens.cart.CartScreen
import com.example.huertohogarapp.ui.screens.products.ProductDetailScreen
import com.example.huertohogarapp.viewmodel.CartViewModel
import com.example.huertohogarapp.viewmodel.ProductViewModel
import com.example.huertohogarapp.viewmodel.UserViewModel

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object ProductList : Screen("product_list")
    object Cart : Screen("cart")
    object Profile : Screen("profile")
}

@Composable
fun AppNavigation(
    productViewModel: ProductViewModel,     // 👉 ViewModel que viene desde MainActivity
) {
    val navController = rememberNavController()

    // Estos ViewModels sí se crean internos (no usan factory)
    val cartViewModel: CartViewModel = viewModel()
    val userViewModel: UserViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "welcome"
    ) {
        composable("welcome") {
            WelcomeScreen(navController = navController)
        }

        composable("home") {
            HomeScreen(
                navController = navController,
                viewModel = productViewModel,
                cartViewModel = cartViewModel,
                onProductClick = { product ->
                    navController.navigate("detalle/${product.id}")
                },
                onNavigateToCart = {
                    navController.navigate("cart")
                }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                navController = navController,
                userViewModel = userViewModel
            )
        }

        composable("register") {
            RegisterScreen(
                navController = navController,
                userViewModel = userViewModel
            )
        }

        composable(Screen.ProductList.route) {
            ProductListScreen(
                viewModel = productViewModel,
                onProductClick = { product ->
                    navController.navigate("detalle/${product.id}")
                }
            )
        }

        composable("cart") {
            CartScreen(
                navController = navController,
                cartViewModel = cartViewModel
            )
        }

        composable(
            route = "detalle/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            val product = productViewModel.products.value.find { it.id == productId }

            product?.let {
                ProductDetailScreen(
                    navController = navController,
                    product = it,
                    cartViewModel = cartViewModel
                )
            }
        }
    }
}
