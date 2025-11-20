package pt.iade.ei.baraflygame.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import pt.iade.ei.baraflygame.data.model.Purchase

@Dao
interface PurchaseDao {
    @Insert
    suspend fun insert(purchase: Purchase): Long

    @Query("SELECT * FROM purchases WHERE userId = :userId ORDER BY timestamp DESC")
    suspend fun getByUser(userId: Int): List<Purchase>

    @Query("DELETE FROM purchases WHERE userId = :userId")
    suspend fun clearByUser(userId: Int)
}