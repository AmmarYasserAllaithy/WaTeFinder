package app.ammar.watefinder.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


const val TABLE_NAME = "history_table"

@Entity(TABLE_NAME)
data class Account(

    @PrimaryKey
    var number: String,
    var displayFormat: String,
    var message: String = "",
    val created: Long = System.currentTimeMillis()

) : Serializable