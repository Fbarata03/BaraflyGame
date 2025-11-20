package pt.iade.ei.baraflygame.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import pt.iade.ei.baraflygame.data.model.User
import pt.iade.ei.baraflygame.data.repository.UserRepository

class RegisterViewModel(private val repo: UserRepository) : ViewModel() {
    private val _username = MutableStateFlow("")
    val username = _username

    private val _email = MutableStateFlow("")
    val email = _email

    private val _password = MutableStateFlow("")
    val password = _password

    fun setUsername(v: String) { _username.value = v }
    fun setEmail(v: String) { _email.value = v }
    fun setPassword(v: String) { _password.value = v }

    fun register(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val user = User(username = username.value, email = email.value, password = password.value)
            val id = repo.register(user)
            if (id > 0) onSuccess()
        }
    }
}