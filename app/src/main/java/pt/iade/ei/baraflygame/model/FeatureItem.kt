package pt.iade.ei.baraflygame.model

import androidx.compose.ui.graphics.Color

data class FeatureItem(
    val title: String,
    val color: Color,
    val imageUrl: String? = null
)