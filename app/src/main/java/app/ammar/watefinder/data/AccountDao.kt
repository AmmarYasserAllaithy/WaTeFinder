package app.ammar.watefinder.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(account: Account)

    @Delete
    suspend fun delete(account: Account): Int

    @Query("SELECT * FROM $TABLE_NAME ORDER BY created DESC")
    fun getAll(): LiveData<List<Account>>

}