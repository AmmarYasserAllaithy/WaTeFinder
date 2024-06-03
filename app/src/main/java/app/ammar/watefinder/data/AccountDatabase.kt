package app.ammar.watefinder.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [Account::class],
    version = 1,
    exportSchema = false
)
abstract class AccountDatabase : RoomDatabase() {

    abstract val dao: AccountDao

    companion object {
        @Volatile
        private var daoInstance: AccountDao? = null

        private fun buildDatabase(context: Context): AccountDatabase =
            Room.databaseBuilder(
                context.applicationContext,
                AccountDatabase::class.java,
                "watefinder_app_db"
            )
                .fallbackToDestructiveMigration()
                .build()

        fun getDao(context: Context): AccountDao {
            synchronized(this) {
                if (daoInstance == null)
                    daoInstance = buildDatabase(context).dao
                return daoInstance as AccountDao
            }
        }
    }

}