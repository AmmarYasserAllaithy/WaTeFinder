package app.ammar.watefinder.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.ammar.watefinder.R
import app.ammar.watefinder.domain.model.AccountModel
import app.ammar.watefinder.domain.repository.AccountRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class MainViewModel(private val repository: AccountRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(MainContract.State())
    val uiState: StateFlow<MainContract.State> = _uiState
        .combine(repository.getAllAccounts()) { state, history ->
            state.copy(history = history)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MainContract.State(),
        )

    private val _effects = MutableSharedFlow<MainContract.Effect>()
    val effects = _effects.asSharedFlow()


    fun onNumberChange(newNumber: String) {
        _uiState.update { it.copy(number = newNumber, isNumberError = false) }
    }

    fun onMessageChange(newMessage: String) {
        _uiState.update { it.copy(message = newMessage) }
    }

    fun onFindClicked(isTe: Boolean) {
        val currentState = _uiState.value
        val cleaned = currentState.number.replace("\\D+".toRegex(), "")

        if (cleaned.length < 11) {
            _uiState.update { it.copy(isNumberError = true) }

            viewModelScope.launch {
                _effects.emit(MainContract.Effect.ShowToast(R.string.invalid_phone))
            }

            return
        }

        val formattedNumber =
            if (cleaned.length == 11 && cleaned.startsWith("01")) "2$cleaned"
            else cleaned
        val account = AccountModel(formattedNumber, currentState.number, currentState.message)

        viewModelScope.launch {
            repository.upsertAccount(account)

            val url = buildUrl(formattedNumber, currentState.message, isTe)

            _effects.emit(MainContract.Effect.OpenUrl(url))
        }
    }

    fun onDeleteAccount(account: AccountModel) {
        viewModelScope.launch {
            repository.deleteAccount(account)

            _effects.emit(MainContract.Effect.ShowToast(R.string.deleted))
        }
    }

    fun onHistoryItemClicked(account: AccountModel) {
        _uiState.update {
            it.copy(
                number = account.displayFormat,
                message = account.message,
                isNumberError = false,
            )
        }
    }


    private fun buildUrl(number: String, message: String, isTe: Boolean) =
        if (isTe) "https://t.me/+$number?text=$message"
        else "https://api.whatsapp.com/send?phone=+$number&text=$message"

}