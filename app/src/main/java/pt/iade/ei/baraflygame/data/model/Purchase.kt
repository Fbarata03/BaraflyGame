package pt.iade.ei.baraflygame.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "purchases")
data class Purchase(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val itemId: Int,
    val timestamp: Long
)