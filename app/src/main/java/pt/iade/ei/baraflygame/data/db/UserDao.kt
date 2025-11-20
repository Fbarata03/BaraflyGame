package pt.iade.ei.baraflygame.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import pt.iade.ei.baraflygame.data.model.User

@Dao
interface UserDao {
    @Insert
    suspend fun register(user: User): Long

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): User?
}