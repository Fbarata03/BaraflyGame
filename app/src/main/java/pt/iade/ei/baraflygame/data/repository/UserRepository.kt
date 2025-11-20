package pt.iade.ei.baraflygame.data.repository

import pt.iade.ei.baraflygame.data.db.UserDao
import pt.iade.ei.baraflygame.data.model.User

class UserRepository(private val dao: UserDao) {
    suspend fun login(email: String, password: String) = dao.login(email, password)
    suspend fun register(user: User) = dao.register(user)
}