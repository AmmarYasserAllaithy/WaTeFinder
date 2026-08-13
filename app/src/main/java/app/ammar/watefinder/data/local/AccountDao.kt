package app.ammar.watefinder.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.ammar.watefinder.data.local.entity.AccountEntity
import app.ammar.watefinder.data.local.entity.TABLE_NAME
import kotlinx.coroutines.flow.Flow


@Dao
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(account: AccountEntity)

    @Delete
    suspend fun delete(account: AccountEntity): Int

    @Query("SELECT * FROM $TABLE_NAME ORDER BY created DESC")
    fun getAll(): Flow<List<AccountEntity>>

}