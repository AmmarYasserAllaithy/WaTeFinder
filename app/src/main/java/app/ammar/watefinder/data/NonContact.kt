package app.ammar.watefinder.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "history_table")
data class NonContact(

	@PrimaryKey
	var number: String,
	var format: String,
	var message: String = "",
	val created: Long = System.currentTimeMillis()

) : Serializable