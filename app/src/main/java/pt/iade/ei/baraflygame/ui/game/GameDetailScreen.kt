package pt.iade.ei.baraflygame.ui.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import android.widget.Toast
import pt.iade.ei.baraflygame.Session
import pt.iade.ei.baraflygame.data.db.AppDatabase
import kotlinx.coroutines.launch

data class GameItem(val name: String, val price: Double, val stock: Int, val imageUrl: String, val tag: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetailScreen(gameId: Int, onBack: () -> Unit) {
    val context = LocalContext.current
    val gameTitle = if (gameId == 1) "Corredores F1" else "Lendas de MotoGP"
    val bannerUrl = if (gameId == 1) {
        "https://images.unsplash.com/photo-1568605117036-5fe5e7bab0b7?w=1200&h=500&fit=crop"
    } else {
        "https://images.unsplash.com/photo-1558980663-3685c1d673c4?w=1200&h=500&fit=crop"
    }

    val items = if (gameId == 1) {
        listOf(
            GameItem(
                name = "Camisa F1 Team",
                price = 79.99,
                stock = 120,
                imageUrl = "https://images.unsplash.com/photo-1512436991641-6745cdb1723f?w=800&h=600&fit=crop",
                tag = "camisa"
            ),
            GameItem(
                name = "Chapéu F1 Team",
                price = 39.99,
                stock = 200,
                imageUrl = "https://images.unsplash.com/photo-1520975916090-3105956dac38?w=800&h=600&fit=crop",
                tag = "chapéu"
            ),
            GameItem(
                name = "Casaco F1 Team",
                price = 129.99,
                stock = 60,
                imageUrl = "https://images.unsplash.com/photo-1541099649105-f69ad21f8f0f?w=800&h=600&fit=crop",
                tag = "casaco"
            )
        )
    } else {
        listOf(
            GameItem(
                name = "Luvas MotoGP",
                price = 49.99,
                stock = 180,
                imageUrl = "https://images.unsplash.com/photo-1608571425586-4c32f409775d?w=800&h=600&fit=crop",
                tag = "luvas"
            ),
            GameItem(
                name = "Capacete MotoGP",
                price = 299.99,
                stock = 90,
                imageUrl = "https://images.unsplash.com/photo-1517144751061-389a1f2c93f6?w=800&h=600&fit=crop",
                tag = "capacete"
            ),
            GameItem(
                name = "Fato de Corrida MotoGP",
                price = 399.99,
                stock = 40,
                imageUrl = "https://images.unsplash.com/photo-1516426129305-7ce174f79ebf?w=800&h=600&fit=crop",
                tag = "fato"
            )
        )
    }

    val sheetOpen = remember { mutableStateOf(false) }
    val selectedItem = remember { mutableStateOf<GameItem?>(null) }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxWidth().height(220.dp)) {
            AsyncImage(
                model = bannerUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.25f))) {}
            Button(onClick = onBack, modifier = Modifier.align(Alignment.TopStart).padding(12.dp), colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.2f))) {
                Text("← Voltar", color = Color.White)
            }
            Box(modifier = Modifier.align(Alignment.TopEnd).padding(12.dp).clip(RoundedCornerShape(12.dp)).background(Color(0xFFE10600)).padding(horizontal = 10.dp, vertical = 6.dp)) {
                Text(text = if (gameId == 1) "F1" else "MotoGP", color = Color.White, fontWeight = FontWeight.SemiBold)
            }
            Column(modifier = Modifier.align(Alignment.BottomStart).padding(16.dp)) {
                Text(gameTitle, color = Color.White, style = MaterialTheme.typography.titleLarge)
                Text(if (gameId == 1) "Experiência definitiva de corridas de Fórmula 1 com os melhores pilotos do mundo." else "Campeonato de duas rodas com os melhores pilotos do mundo.", color = Color.White.copy(alpha = 0.9f), fontSize = MaterialTheme.typography.bodyLarge.fontSize)
            }
        }

        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Column { Text("Temporada", color = Color.Gray, fontSize = MaterialTheme.typography.bodyLarge.fontSize); Text("2024", fontWeight = FontWeight.SemiBold) }
            Column { Text("Itens", color = Color.Gray, fontSize = MaterialTheme.typography.bodyLarge.fontSize); Text("3", fontWeight = FontWeight.SemiBold) }
            Column { Text("Campeão", color = Color.Gray, fontSize = MaterialTheme.typography.bodyLarge.fontSize); Text(if (gameId == 1) "Max Verstappen" else "Pecco Bagnaia", fontWeight = FontWeight.SemiBold) }
        }

        Text("Itens Disponíveis", modifier = Modifier.padding(horizontal = 16.dp), style = MaterialTheme.typography.titleMedium)

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items) { item ->
                Card(shape = RoundedCornerShape(16.dp), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        val img = when {
                            item.name.contains("Luvas", true) -> "https://images.unsplash.com/photo-1562158075-6b1f75aa2b4a?w=800&h=600&fit=crop"
                            item.name.contains("Capacete", true) -> "https://images.unsplash.com/photo-1517144751061-389a1f2c93f6?w=800&h=600&fit=crop"
                            item.name.contains("Fato", true) -> "https://images.unsplash.com/photo-1516426129305-7ce174f79ebf?w=800&h=600&fit=crop"
                            item.name.contains("Camisa", true) || item.name.contains("Camisola", true) -> "https://images.unsplash.com/photo-1512436991641-6745cdb1723f?w=800&h=600&fit=crop"
                            item.name.contains("Chapéu", true) || item.name.contains("Boné", true) -> "https://images.unsplash.com/photo-1520975916090-3105956dac38?w=800&h=600&fit=crop"
                            item.name.contains("Casaco", true) -> "https://images.unsplash.com/photo-1541099649105-f69ad21f8f0f?w=800&h=600&fit=crop"
                            else -> item.imageUrl
                        }
                        AsyncImage(model = img, contentDescription = null, modifier = Modifier.fillMaxWidth().height(120.dp).clip(RoundedCornerShape(12.dp)), contentScale = ContentScale.Crop)
                        Row(modifier = Modifier.padding(top = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                            val tagBg = when (item.tag.lowercase()) {
                                "lendário" -> Color(0xFFFFF3E0)
                                "épico" -> Color(0xFFEDE7F6)
                                "raro" -> Color(0xFFE3F2FD)
                                else -> Color(0xFFF5F5F5)
                            }
                            val tagFg = when (item.tag.lowercase()) {
                                "lendário" -> Color(0xFFEF6C00)
                                "épico" -> Color(0xFF6A1B9A)
                                "raro" -> Color(0xFF1565C0)
                                else -> Color.DarkGray
                            }
                            Box(modifier = Modifier.clip(RoundedCornerShape(10.dp)).background(tagBg).padding(horizontal = 8.dp, vertical = 4.dp)) { Text(item.tag, color = tagFg, fontSize = MaterialTheme.typography.bodyLarge.fontSize * 0.8f) }
                        }
                        Text(item.name, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 6.dp))
                        Text("🪙 " + String.format("%.0f", item.price), color = Color(0xFFFFC107), modifier = Modifier.padding(top = 2.dp))
                        Text("Estoque: ${item.stock}", color = Color.Gray, modifier = Modifier.padding(top = 2.dp))
                        Button(onClick = { selectedItem.value = item; sheetOpen.value = true }, modifier = Modifier.fillMaxWidth().padding(top = 6.dp), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)) {
                            Text("Comprar")
                        }
                    }
                }
            }
        }

        if (sheetOpen.value && selectedItem.value != null) {
            ModalBottomSheet(onDismissRequest = { sheetOpen.value = false }) {
                val it = selectedItem.value!!
                Column(modifier = Modifier.padding(16.dp)) {
                    val img2 = when {
                        it.name.contains("Luvas", true) -> "https://images.unsplash.com/photo-1562158075-6b1f75aa2b4a?w=800&h=600&fit=crop"
                        it.name.contains("Capacete", true) -> "https://images.unsplash.com/photo-1517144751061-389a1f2c93f6?w=800&h=600&fit=crop"
                        it.name.contains("Fato", true) -> "https://images.unsplash.com/photo-1516426129305-7ce174f79ebf?w=800&h=600&fit=crop"
                        it.name.contains("Camisa", true) || it.name.contains("Camisola", true) -> "https://images.unsplash.com/photo-1512436991641-6745cdb1723f?w=800&h=600&fit=crop"
                        it.name.contains("Chapéu", true) || it.name.contains("Boné", true) -> "https://images.unsplash.com/photo-1520975916090-3105956dac38?w=800&h=600&fit=crop"
                        it.name.contains("Casaco", true) -> "https://images.unsplash.com/photo-1541099649105-f69ad21f8f0f?w=800&h=600&fit=crop"
                        else -> it.imageUrl
                    }
                    AsyncImage(model = img2, contentDescription = null, modifier = Modifier.fillMaxWidth().height(160.dp).clip(RoundedCornerShape(16.dp)), contentScale = ContentScale.Crop)
                    Text(it.name, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 12.dp))
                    Text("🪙 " + String.format("%.0f", it.price), color = Color(0xFFFFC107), fontWeight = FontWeight.SemiBold)
                    Text("Estoque: ${it.stock}", color = Color.Gray, modifier = Modifier.padding(top = 4.dp))
                    Button(onClick = {
                        scope.launch {
                            val db = AppDatabase.getInstance(context)
                            val itemsDao = db.itemDao()
                            val purchaseDao = db.purchaseDao()
                            val allItems = itemsDao.getAll()
                            val dbItem = allItems.firstOrNull { dbi -> dbi.name == it.name }
                            val itemId = if (dbItem != null) dbItem.id else {
                                itemsDao.insertAll(listOf(pt.iade.ei.baraflygame.data.model.PurchasableItem(name = it.name, price = it.price)))
                                itemsDao.getAll().first { x -> x.name == it.name }.id
                            }
                            val uid = if (Session.userId != 0) Session.userId else db.userDao().login("barafly@iade.pt", "12345")?.id ?: 0
                            purchaseDao.insert(pt.iade.ei.baraflygame.data.model.Purchase(userId = uid, itemId = itemId, timestamp = System.currentTimeMillis()))
                            Toast.makeText(context, "Acabou de adquirir o item ${it.name} por ${String.format("%.0f", it.price)} moedas", Toast.LENGTH_SHORT).show()
                            sheetOpen.value = false
                        }
                    }, modifier = Modifier.fillMaxWidth().padding(top = 12.dp), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)) { Text("Adquirir Agora") }
                }
            }
        }
    }
}