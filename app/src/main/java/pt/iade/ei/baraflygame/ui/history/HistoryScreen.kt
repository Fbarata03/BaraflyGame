package pt.iade.ei.baraflygame.ui.history

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.iade.ei.baraflygame.data.db.AppDatabase
import pt.iade.ei.baraflygame.data.model.Purchase
import pt.iade.ei.baraflygame.data.model.PurchasableItem
import pt.iade.ei.baraflygame.Session
import android.widget.Toast
import java.text.NumberFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun HistoryScreen() {
    val context = LocalContext.current
    val db = remember { AppDatabase.getInstance(context) }
    val purchasesState = remember { mutableStateOf<List<Purchase>>(emptyList()) }
    val itemsState = remember { mutableStateOf<List<PurchasableItem>>(emptyList()) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        val items = db.itemDao().getAll()
        itemsState.value = items
        val uId = if (Session.userId != 0) Session.userId else db.userDao().login("barafly@iade.pt", "12345")?.id ?: 0
        val purchases = db.purchaseDao().getByUser(uId)
        purchasesState.value = purchases
    }

    val purchases = purchasesState.value
    val items = itemsState.value
    val totalPurchases = purchases.size
    val totalSpent = purchases.sumOf { p -> items.firstOrNull { it.id == p.itemId }?.price ?: 0.0 }
    val categories = purchases.map { p -> items.firstOrNull { it.id == p.itemId }?.name ?: "" }
        .map { name -> if (name.contains("F1", true)) "F1" else if (name.contains("MotoGP", true)) "MotoGP" else "Outros" }
        .toSet().size
    val currency = "🪙 " + String.format("%.0f", totalSpent)

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.horizontalGradient(listOf(Color(0xFFE10600), Color(0xFFB00000))))
                .padding(16.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(48.dp).clip(RoundedCornerShape(24.dp)).background(Color.White.copy(alpha = 0.2f)), contentAlignment = Alignment.Center) {
                        Text("👤", fontSize = 22.sp)
                    }
                    Column(modifier = Modifier.padding(start = 12.dp)) {
                        Text("BaraflyGame", color = Color.White, fontWeight = FontWeight.SemiBold)
                        Text("Histórico de Compras", color = Color(0xFFFFCDD2), fontSize = 12.sp)
                    }
                }
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                StatTile(bg = Color(0xFFFFEBEE), emoji = "🛍️", label = "Total de Compras", value = totalPurchases.toString(), modifier = Modifier.weight(1f))
                StatTile(bg = Color(0xFFE8F5E9), emoji = "🪙", label = "Moedas Gastas", value = currency, modifier = Modifier.weight(1f))
                StatTile(bg = Color(0xFFEDE7F6), emoji = "⭐", label = "Categorias", value = categories.toString(), modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    scope.launch {
                        val uId = if (Session.userId != 0) Session.userId else db.userDao().login("barafly@iade.pt", "12345")?.id ?: 0
                        db.purchaseDao().clearByUser(uId)
                        purchasesState.value = emptyList()
                        Toast.makeText(context, "Histórico limpo", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) { Text("Limpar Histórico") }
            Spacer(modifier = Modifier.height(16.dp))
            Card(shape = RoundedCornerShape(16.dp), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp), modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    if (purchases.isEmpty()) {
                        Text("👜", fontSize = 42.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Nenhuma compra ainda", style = MaterialTheme.typography.titleMedium, textAlign = TextAlign.Center)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Suas compras aparecerão aqui", color = Color.Gray, fontSize = 12.sp, textAlign = TextAlign.Center)
                    } else {
                        purchases.forEach { p ->
                            val item = items.firstOrNull { it.id == p.itemId }
                            val date = Instant.ofEpochMilli(p.timestamp).atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("dd MMM yyyy", Locale("pt", "BR")))
                            HistoryRow(name = item?.name ?: "Item", price = "🪙 " + String.format("%.0f", item?.price ?: 0.0), date = date)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StatTile(bg: Color, emoji: String, label: String, value: String, modifier: Modifier = Modifier) {
    Card(shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = bg), modifier = modifier) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(36.dp).clip(RoundedCornerShape(8.dp)).background(Color.White.copy(alpha = 0.6f)), contentAlignment = Alignment.Center) { Text(emoji) }
            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(label, color = Color.Gray, fontSize = 12.sp)
                Text(value, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
private fun HistoryRow(name: String, price: String, date: String) {
    Card(shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F7F7)), modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(36.dp).clip(RoundedCornerShape(8.dp)).background(Color.White.copy(alpha = 0.6f)), contentAlignment = Alignment.Center) { Text("🛒") }
            Column(modifier = Modifier.padding(start = 8.dp).weight(1f)) {
                Text(name, fontWeight = FontWeight.SemiBold)
                Text(date, color = Color.Gray, fontSize = 12.sp)
            }
            Text(price, fontWeight = FontWeight.SemiBold)
        }
    }
}