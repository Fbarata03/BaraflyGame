package pt.iade.ei.baraflygame.controller

import androidx.compose.ui.graphics.Color
import pt.iade.ei.baraflygame.model.FeatureItem

class HomeController {
    fun getHighlights(): List<FeatureItem> = listOf(
        FeatureItem(
            title = "Corredores F1",
            color = Color(0xFFE3F2FD),
            imageUrl = "https://images.unsplash.com/photo-1568605117036-5fe5e7bab0b7?w=800&h=400&fit=crop"
        ),
        FeatureItem(
            title = "Lendas de MotoGP",
            color = Color(0xFFFFEBEE),
            imageUrl = "https://images.unsplash.com/photo-1558980663-3685c1d673c4?w=800&h=400&fit=crop"
        )
    )
}