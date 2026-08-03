package app.ammar.watefinder.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


const val TABLE_NAME = "history_table"


@Entity(tableName = TABLE_NAME)
data class AccountEntity(
    @PrimaryKey val number: String,
    val displayFormat: String,
    val message: String = "",
    val created: Long = System.currentTimeMillis(),
)
