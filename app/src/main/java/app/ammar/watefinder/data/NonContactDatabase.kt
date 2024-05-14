package app.ammar.watefinder.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
	entities = [NonContact::class], 
	version = 7,
	exportSchema = false
)
abstract class NonContactDatabase : RoomDatabase() {

    abstract val dao: NonContactDao

    companion object {
        @Volatile
        private var daoInstance: NonContactDao? = null

        fun buildDatabase(context: Context): NonContactDatabase = 
			Room.databaseBuilder(
				context.applicationContext,
				NonContactDatabase::class.java,
				"watefinder_db"
            )
			.fallbackToDestructiveMigration()
			.build()
		
		fun getDao(context: Context): NonContactDao {
			synchronized(this) {
				if (daoInstance == null)
					daoInstance = buildDatabase(context).dao
				
				return daoInstance as NonContactDao
			}
		}
    }

}