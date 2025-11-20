package pt.iade.ei.baraflygame.controller

import pt.iade.ei.baraflygame.data.model.User
import pt.iade.ei.baraflygame.data.repository.UserRepository
import pt.iade.ei.baraflygame.model.AuthResult

class AuthController(private val repo: UserRepository) {
    suspend fun login(email: String, password: String): AuthResult {
        val user = repo.login(email, password)
        return if (user != null) AuthResult(true) else AuthResult(false, "Credenciais inválidas")
    }

    suspend fun register(username: String, email: String, password: String): AuthResult {
        val id = repo.register(User(username = username, email = email, password = password))
        return if (id > 0) AuthResult(true) else AuthResult(false, "Falha ao registar")
    }
}