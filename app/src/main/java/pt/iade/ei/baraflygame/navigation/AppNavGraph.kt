package pt.iade.ei.baraflygame.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pt.iade.ei.baraflygame.ui.home.HomeScreen
import pt.iade.ei.baraflygame.ui.game.GameDetailScreen
import pt.iade.ei.baraflygame.ui.game.GameListScreen
import pt.iade.ei.baraflygame.ui.history.HistoryScreen
import pt.iade.ei.baraflygame.ui.login.LoginScreen
import pt.iade.ei.baraflygame.ui.login.LoginViewModel
import pt.iade.ei.baraflygame.ui.profile.ProfileScreen
import pt.iade.ei.baraflygame.ui.register.RegisterScreen
import pt.iade.ei.baraflygame.ui.register.RegisterViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    registerViewModel: RegisterViewModel
) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                viewModel = loginViewModel,
                onLoginSuccess = { navController.navigate("home") },
                onRegisterClick = { navController.navigate("register") }
            )
        }
        composable("register") {
            RegisterScreen(
                viewModel = registerViewModel,
                onRegisterSuccess = { navController.popBackStack(); navController.navigate("home") }
            )
        }
        composable("home") {
            HomeScreen(
                onLogout = { navController.navigate("login") },
                onOpenGames = { navController.navigate("games") },
                onOpenHistory = { navController.navigate("history") },
                onOpenProfile = { navController.navigate("profile") },
                onOpenGameDetail = { id -> navController.navigate("games/$id") }
            )
        }
        composable("games") { GameListScreen(onGameClick = { id -> navController.navigate("games/$id") }) }
        composable("games/{gameId}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("gameId")?.toIntOrNull() ?: 0
            GameDetailScreen(gameId = id, onBack = { navController.popBackStack() })
        }
        composable("history") { HistoryScreen() }
        composable("profile") {
            ProfileScreen(
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onOpenGames = { navController.navigate("home") }
            )
        }
    }
}