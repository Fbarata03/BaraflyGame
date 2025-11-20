package pt.iade.ei.baraflygame.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import pt.iade.ei.baraflygame.data.db.AppDatabase
import pt.iade.ei.baraflygame.data.model.Purchase
import pt.iade.ei.baraflygame.data.model.PurchasableItem

@Composable
fun ProfileScreen(onLogout: () -> Unit, onOpenGames: () -> Unit) {
    val context = LocalContext.current
    val db = remember { AppDatabase.getInstance(context) }
    val purchasesState = remember { mutableStateOf<List<Purchase>>(emptyList()) }
    val itemsState = remember { mutableStateOf<List<PurchasableItem>>(emptyList()) }
    val userNameState = remember { mutableStateOf("Utilizador") }

    LaunchedEffect(Unit) {
        val user = db.userDao().login("barafly@iade.pt", "12345")
        if (user != null) userNameState.value = user.username
        val items = db.itemDao().getAll()
        itemsState.value = items
        val uId = user?.id ?: 0
        val purchases = db.purchaseDao().getByUser(uId)
        purchasesState.value = purchases
    }

    val purchases = purchasesState.value
    val items = itemsState.value
    val totalPurchases = purchases.size
    val totalSpent = purchases.sumOf { p -> items.firstOrNull { it.id == p.itemId }?.price ?: 0.0 }
    val favoriteItem = purchases.groupBy { p -> items.firstOrNull { it.id == p.itemId }?.name ?: "Nenhum" }
        .maxByOrNull { it.value.size }?.key ?: "Nenhum"

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.horizontalGradient(listOf(Color(0xFFE10600), Color(0xFFB00000))))
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(48.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.2f)), contentAlignment = Alignment.Center) {
                    Text("👤", fontSize = 22.sp)
                }
                Column(modifier = Modifier.padding(start = 12.dp)) {
                    Text("BaraflyGame", color = Color.White, fontWeight = FontWeight.SemiBold)
                    Text("Bem-vindo, ${'$'}{userNameState.value}!", color = Color(0xFFFFCDD2), fontSize = 12.sp)
                }
                
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Card(shape = RoundedCornerShape(16.dp), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp), modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Estatísticas", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                        StatTile(bg = Color(0xFFFFEBEE), emoji = "🛍️", label = "Total de Compras", value = totalPurchases.toString(), modifier = Modifier.weight(1f))
                        StatTile(bg = Color(0xFFE8F5E9), emoji = "🪙", label = "Moedas Gastas", value = "🪙 " + String.format("%.0f", totalSpent), modifier = Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                        StatTile(bg = Color(0xFFEDE7F6), emoji = "⭐", label = "Item Favorito", value = favoriteItem, modifier = Modifier.weight(1f))
                        StatTile(bg = Color(0xFFE3F2FD), emoji = "🏆", label = "Conquistas", value = if (totalPurchases >= 5) "Colecionador" else if (totalPurchases >= 1) "Primeiro Comprador" else "Nenhuma", modifier = Modifier.weight(1f))
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Card(shape = RoundedCornerShape(16.dp), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp), modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Conquistas", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    AchievementRow(title = "Primeiro Comprador", desc = if (totalPurchases >= 1) "Completado! 🎉" else "Faça sua primeira compra", unlocked = totalPurchases >= 1, color = Color(0xFFFFF9C4))
                    AchievementRow(title = "Colecionador", desc = if (totalPurchases >= 5) "Completado! 🎉" else "Compre 5 itens (${totalPurchases}/5)", unlocked = totalPurchases >= 5, color = Color(0xFFE3F2FD))
                    AchievementRow(title = "Grande Gastador", desc = if (totalSpent >= 1000.0) "Completado! 🎉" else "Gaste 1000 moedas (🪙" + String.format("%.0f", totalSpent) + "/1000)", unlocked = totalSpent >= 1000.0, color = Color(0xFFE8F5E9))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Card(shape = RoundedCornerShape(16.dp), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp), modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Informações da Conta", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(12.dp))
                    InfoRow(label = "Nome de Usuário", value = userNameState.value)
                    val joined = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM 'de' yyyy", Locale("pt", "BR")))
                    InfoRow(label = "Membro Desde", value = joined)
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Status", color = Color.Gray)
                        Box(modifier = Modifier.clip(RoundedCornerShape(12.dp)).background(Color(0xFFE8F5E9)).padding(horizontal = 10.dp, vertical = 4.dp)) {
                            Text("Ativo", color = Color(0xFF2E7D32), fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = onOpenGames,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier.fillMaxWidth(),
            ) { Text("Explorar Jogos") }

            Spacer(modifier = Modifier.height(12.dp))
            OutlinedButton(
                onClick = onLogout,
                modifier = Modifier.fillMaxWidth()
            ) { Text("Terminar Sessão") }
        }
    }
}

@Composable
private fun StatTile(bg: Color, emoji: String, label: String, value: String, modifier: Modifier = Modifier) {
    Card(shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = bg), modifier = modifier) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(36.dp).clip(RoundedCornerShape(8.dp)).background(Color.White.copy(alpha = 0.6f)), contentAlignment = Alignment.Center) {
                Text(emoji)
            }
            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(label, color = Color.Gray, fontSize = 12.sp)
                Text(value, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
private fun AchievementRow(title: String, desc: String, unlocked: Boolean, color: Color) {
    val bg = if (unlocked) color else Color(0xFFF5F5F5)
    val alpha = if (unlocked) 1f else 0.7f
    Card(shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = bg), modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(36.dp).clip(RoundedCornerShape(8.dp)).background(bg.copy(alpha = 0.6f)), contentAlignment = Alignment.Center) { Text("🏅") }
            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(title, fontWeight = FontWeight.SemiBold, modifier = Modifier, color = Color.Black.copy(alpha = alpha))
                Text(desc, color = Color.DarkGray.copy(alpha = alpha), fontSize = 12.sp)
            }
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = Color.Gray)
        Text(value)
    }
}