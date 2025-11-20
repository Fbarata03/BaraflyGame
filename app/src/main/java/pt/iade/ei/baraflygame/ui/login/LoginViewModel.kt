package pt.iade.ei.baraflygame.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import pt.iade.ei.baraflygame.data.repository.UserRepository

class LoginViewModel(private val repo: UserRepository) : ViewModel() {
    private val _email = MutableStateFlow("")
    val email = _email

    private val _password = MutableStateFlow("")
    val password = _password

    val loginState = MutableStateFlow(false)
    val currentUserId = MutableStateFlow<Int?>(null)

    fun setEmail(v: String) { _email.value = v }
    fun setPassword(v: String) { _password.value = v }

    fun login(onResult: (Boolean, Int?) -> Unit) {
        viewModelScope.launch {
            val user = repo.login(email.value, password.value)
            val ok = user != null
            loginState.value = ok
            currentUserId.value = user?.id
            onResult(ok, user?.id)
        }
    }
}