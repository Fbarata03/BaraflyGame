package pt.iade.ei.baraflygame.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pt.iade.ei.baraflygame.controller.HomeController
import pt.iade.ei.baraflygame.view.HighlightCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onLogout: () -> Unit,
    onOpenGames: () -> Unit,
    onOpenHistory: () -> Unit,
    onOpenProfile: () -> Unit,
    onOpenGameDetail: (Int) -> Unit
) {
    val selected = remember { mutableStateOf(0) }
    val controller = remember { HomeController() }
    val itemsList = controller.getHighlights()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("BaraflyGame", fontWeight = FontWeight.SemiBold) },
                actions = {
                IconButton(onClick = { }) { Text("🔔") }
                IconButton(onClick = { }) { Text("⚙️") }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selected.value == 0,
                    onClick = { selected.value = 0 },
                    icon = { Text("⭐") },
                    label = { Text("Destaques") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF6A1B9A),
                        selectedTextColor = Color(0xFF6A1B9A)
                    )
                )
                NavigationBarItem(
                    selected = selected.value == 1,
                    onClick = { selected.value = 1; onOpenHistory() },
                    icon = { Text("🕘") },
                    label = { Text("Histórico") }
                )
                NavigationBarItem(
                    selected = selected.value == 2,
                    onClick = { selected.value = 2; onOpenProfile() },
                    icon = { Text("👤") },
                    label = { Text("Perfil") }
                )
            }
        }
    ) { inner ->
        if (selected.value == 0) {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(inner).padding(horizontal = 16.dp),
                contentPadding = PaddingValues(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(itemsList) { item ->
                    HighlightCard(
                        title = item.title,
                        backgroundColor = item.color,
                        imageUrl = item.imageUrl,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            val id = if (item.title.contains("F1", ignoreCase = true)) 1 else 2
                            onOpenGameDetail(id)
                        }
                    )
                }
            }
        }
    }
}