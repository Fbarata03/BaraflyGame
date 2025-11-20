package pt.iade.ei.baraflygame.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pt.iade.ei.baraflygame.data.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.iade.ei.baraflygame.data.model.Purchase
import pt.iade.ei.baraflygame.data.model.PurchasableItem

@Database(entities = [User::class, PurchasableItem::class, Purchase::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun purchaseDao(): PurchaseDao
    abstract fun itemDao(): PurchasableItemDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "baraflygame.db"
                ).build()
                INSTANCE = instance
                CoroutineScope(Dispatchers.IO).launch {
                    val dao = instance.userDao()
                    val exists = dao.login("barafly@iade.pt", "12345")
                    if (exists == null) {
                        dao.register(User(username = "Demo", email = "barafly@iade.pt", password = "12345"))
                    }
                    val itemDao = instance.itemDao()
                    val currentItems = itemDao.getAll()
                    if (currentItems.isEmpty()) {
                        itemDao.insertAll(
                            listOf(
                                PurchasableItem(name = "Motor V6 Turbo Elite", price = 2499.99),
                                PurchasableItem(name = "Skin Red Bull Racing 2024", price = 45.99),
                                PurchasableItem(name = "Passe VIP Paddock", price = 599.99),
                                PurchasableItem(name = "Capacete MotoGP Pro", price = 299.99),
                                PurchasableItem(name = "Kit Racing Yamaha 2024", price = 159.99),
                                PurchasableItem(name = "Passe VIP Box MotoGP", price = 499.99)
                            )
                        )
                    }
                }
                instance
            }
        }
    }
}