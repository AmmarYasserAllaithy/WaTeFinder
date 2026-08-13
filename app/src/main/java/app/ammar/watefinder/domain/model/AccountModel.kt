package app.ammar.watefinder.domain.model


data class AccountModel(
    val number: String,
    val displayFormat: String,
    val message: String = "",
    val created: Long = System.currentTimeMillis()
)
