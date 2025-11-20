package pt.iade.ei.baraflygame.view

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import pt.iade.ei.baraflygame.ui.theme.BaraflyGameTheme

@Composable
fun BottomNavBar(selectedIndex: Int, onSelect: (Int) -> Unit) {
    NavigationBar {
        NavigationBarItem(
            selected = selectedIndex == 0,
            onClick = { onSelect(0) },
            icon = { Text("⭐") },
            label = { Text("Destaques") }
        )
        NavigationBarItem(
            selected = selectedIndex == 1,
            onClick = { onSelect(1) },
            icon = { Text("🕘") },
            label = { Text("Histórico") }
        )
        NavigationBarItem(
            selected = selectedIndex == 2,
            onClick = { onSelect(2) },
            icon = { Text("👤") },
            label = { Text("Perfil") }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavBarPreview() {
    BaraflyGameTheme {
        BottomNavBar(selectedIndex = 0, onSelect = {})
    }
}