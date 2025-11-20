package pt.iade.ei.baraflygame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import pt.iade.ei.baraflygame.data.db.AppDatabase
import pt.iade.ei.baraflygame.data.repository.UserRepository
import pt.iade.ei.baraflygame.navigation.AppNavGraph
import pt.iade.ei.baraflygame.ui.login.LoginViewModel
import pt.iade.ei.baraflygame.ui.register.RegisterViewModel
import pt.iade.ei.baraflygame.ui.theme.BaraflyGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BaraflyGameTheme {
                val db = remember { AppDatabase.getInstance(this) }
                val repo = remember { UserRepository(db.userDao()) }
                val loginVM = remember { LoginViewModel(repo) }
                val registerVM = remember { RegisterViewModel(repo) }
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) {
                    AppNavGraph(
                        navController = navController,
                        loginViewModel = loginVM,
                        registerViewModel = registerVM
                    )
                }
            }
        }
    }
}