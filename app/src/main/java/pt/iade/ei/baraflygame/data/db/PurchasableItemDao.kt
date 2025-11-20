package pt.iade.ei.baraflygame.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import pt.iade.ei.baraflygame.data.model.PurchasableItem

@Dao
interface PurchasableItemDao {
    @Insert
    suspend fun insertAll(items: List<PurchasableItem>)

    @Query("SELECT * FROM items")
    suspend fun getAll(): List<PurchasableItem>
}