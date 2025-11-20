package pt.iade.ei.baraflygame.view

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import pt.iade.ei.baraflygame.ui.theme.BaraflyGameTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    onBellClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        actions = {
            IconButton(onClick = onBellClick) { Text("🔔") }
            IconButton(onClick = onSettingsClick) { Text("⚙️") }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AppTopBarPreview() {
    BaraflyGameTheme {
        AppTopBar(title = "BaraflyGame", onBellClick = {}, onSettingsClick = {})
    }
}