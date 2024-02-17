package hu.bme.aut.android.fuelingreg.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "car_table")
data class CarEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val description: String,
    val capacity: Int
)