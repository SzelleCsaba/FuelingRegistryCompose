package hu.bme.aut.android.fuelingreg.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import hu.bme.aut.android.fuelingreg.domain.model.Type
import kotlinx.datetime.LocalDate

@Entity(tableName = "fueling_table")
data class FuelingEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val amount: Double,
    val carId: Int,
    val type: Type,
    val pricePerLiter: Int,
    val isFullTank: Boolean,
    val odometerAtFueling: Int,
    val date: LocalDate,
)