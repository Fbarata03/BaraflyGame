package pt.iade.ei.baraflygame.view

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import pt.iade.ei.baraflygame.ui.theme.BaraflyGameTheme

@Composable
fun FormTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    password: Boolean = false
    ) {
    Text(label)
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        visualTransformation = if (password) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
        placeholder = { if (placeholder.isNotEmpty()) Text(placeholder) }
    )
}

@Preview(showBackground = true)
@Composable
fun FormTextFieldPreview() {
    BaraflyGameTheme {
        var v by remember { mutableStateOf("") }
        FormTextField(label = "Email", value = v, onValueChange = { v = it }, placeholder = "seu@email.com")
    }
}