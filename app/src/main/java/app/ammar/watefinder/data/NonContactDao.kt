package app.ammar.watefinder.data

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface NonContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(nonContact: NonContact)

    @Delete
    suspend fun delete(nonContact: NonContact)

    @Query("SELECT * FROM history_table ORDER BY created DESC")
    fun getAll(): LiveData<List<NonContact>>

}