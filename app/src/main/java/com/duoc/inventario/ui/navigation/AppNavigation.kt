package com.duoc.inventario.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.duoc.inventario.data.local.AppDatabase
import com.duoc.inventario.repository.ProductoRepository
import com.duoc.inventario.ui.producto.ListaProductosScreen
import com.duoc.inventario.ui.producto.ProductoFormScreen
import com.duoc.inventario.ui.producto.ProductoViewModel
import com.duoc.inventario.ui.producto.ProductoViewModelFactory

@Composable
fun AppNavigation(database: AppDatabase) {
    val navController = rememberNavController()
    
    val repository = ProductoRepository(database.productoDao())
    val viewModel: ProductoViewModel = viewModel(factory = ProductoViewModelFactory(repository))

    NavHost(navController = navController, startDestination = "lista") {
        composable("lista") {
            ListaProductosScreen(
                viewModel = viewModel,
                onNavigateToForm = { productoId ->
                    navController.navigate("form/$productoId")
                }
            )
        }
        
        composable(
            route = "form/{productoId}",
            arguments = listOf(navArgument("productoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productoId = backStackEntry.arguments?.getInt("productoId") ?: 0
            ProductoFormScreen(
                viewModel = viewModel,
                productoId = productoId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
