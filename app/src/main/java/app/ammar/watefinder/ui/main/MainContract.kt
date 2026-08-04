package app.ammar.watefinder.ui.main

import androidx.annotation.StringRes
import app.ammar.watefinder.domain.model.AccountModel


object MainContract {

    data class State(
        val number: String = "",
        val message: String = "",
        val history: List<AccountModel> = emptyList(),
        val isNumberError: Boolean = false,
    )

    sealed interface Effect {
        data class OpenUrl(val url: String) : Effect
        data class ShareUrl(val url: String) : Effect
        data class ShowToast(@StringRes val messageResId: Int) : Effect
    }

}