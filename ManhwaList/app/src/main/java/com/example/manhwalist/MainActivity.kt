package com.example.manhwalist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.manhwalist.ui.screens.DetailScreen
import com.example.manhwalist.ui.screens.FormScreen
import com.example.manhwalist.ui.screens.HomeScreen
import com.example.manhwalist.ui.theme.ManhwaListTheme
import com.example.manhwalist.viewmodel.ManhwaViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: ManhwaViewModel by viewModels {
        ManhwaViewModel.Factory((application as ManhwaApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ManhwaListTheme {
                ManhwaApp(viewModel)
            }
        }
    }
}

@Composable
fun ManhwaApp(viewModel: ManhwaViewModel) {
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                viewModel = viewModel,
                onAddClick = { navController.navigate("form") },
                onManhwaClick = { id -> navController.navigate("detail/$id") }
            )
        }
        composable(
            route = "detail/{manhwaId}",
            arguments = listOf(navArgument("manhwaId") { type = NavType.IntType })
        ) { backStackEntry ->
            val manhwaId = backStackEntry.arguments?.getInt("manhwaId") ?: return@composable
            DetailScreen(
                manhwaId = manhwaId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onEdit = { id -> navController.navigate("form?manhwaId=$id") },
                onDelete = { navController.popBackStack() }
            )
        }
        composable(
            route = "form?manhwaId={manhwaId}",
            arguments = listOf(navArgument("manhwaId") { 
                type = NavType.IntType
                defaultValue = -1 
            })
        ) { backStackEntry ->
            val manhwaId = backStackEntry.arguments?.getInt("manhwaId")
            val id = if (manhwaId == -1) null else manhwaId
            FormScreen(
                manhwaId = id,
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onSuccess = { navController.popBackStack() }
            )
        }
    }
}
