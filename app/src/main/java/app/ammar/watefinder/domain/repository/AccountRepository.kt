package app.ammar.watefinder.domain.repository

import app.ammar.watefinder.domain.model.AccountModel
import kotlinx.coroutines.flow.Flow


interface AccountRepository {
    fun getAllAccounts(): Flow<List<AccountModel>>
    suspend fun upsertAccount(account: AccountModel)
    suspend fun deleteAccount(account: AccountModel)
}