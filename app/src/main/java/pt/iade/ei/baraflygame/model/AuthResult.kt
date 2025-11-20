package pt.iade.ei.baraflygame.model

data class AuthResult(
    val success: Boolean,
    val message: String? = null
)