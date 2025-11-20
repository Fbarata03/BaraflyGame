package pt.iade.ei.baraflygame.view

import coil.compose.AsyncImage
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import pt.iade.ei.baraflygame.ui.theme.BaraflyGameTheme

@Composable
fun HighlightCard(title: String, backgroundColor: Color, modifier: Modifier = Modifier, onClick: () -> Unit = {}, imageUrl: String? = null) {
    Card(shape = RoundedCornerShape(16.dp), modifier = modifier, elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), onClick = onClick) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.fillMaxWidth().height(180.dp), contentAlignment = Alignment.Center) {
                if (imageUrl != null) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        placeholder = ColorPainter(backgroundColor.copy(alpha = 0.4f)),
                        error = ColorPainter(Color.DarkGray.copy(alpha = 0.5f))
                    )
                } else {
                    Text(" ", color = backgroundColor)
                }
                Box(modifier = Modifier.align(Alignment.TopEnd).padding(12.dp)) {
                    Box(modifier = Modifier.height(36.dp).width(36.dp).align(Alignment.TopEnd)) {
                        androidx.compose.foundation.layout.Box(
                            modifier = Modifier
                                .height(36.dp)
                                .width(36.dp)
                                .align(Alignment.Center)
                                .background(Color(0x88999999), shape = RoundedCornerShape(18.dp))
                        )
                    }
                }
            }
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Start
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HighlightCardPreview() {
    BaraflyGameTheme {
        HighlightCard(title = "Passe F1", backgroundColor = Color(0xFFE10600))
    }
}