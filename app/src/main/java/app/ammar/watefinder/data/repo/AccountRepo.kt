package app.ammar.watefinder.data.repo

import app.ammar.watefinder.data.local.AccountDao
import app.ammar.watefinder.domain.model.AccountModel
import app.ammar.watefinder.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class AccountRepo(private val dao: AccountDao) : AccountRepository {

    override fun getAllAccounts(): Flow<List<AccountModel>> {
        return dao.getAll().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun upsertAccount(account: AccountModel) {
        dao.upsert(account.toEntity())
    }

    override suspend fun deleteAccount(account: AccountModel) {
        dao.delete(account.toEntity())
    }

}