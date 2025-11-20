package pt.iade.ei.baraflygame.view

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pt.iade.ei.baraflygame.ui.theme.BaraflyGameTheme

@Composable
fun PrimaryButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
    ) { Text(text) }
}

@Preview(showBackground = true)
@Composable
fun PrimaryButtonPreview() {
    BaraflyGameTheme {
        PrimaryButton(text = "Entrar", onClick = {})
    }
}