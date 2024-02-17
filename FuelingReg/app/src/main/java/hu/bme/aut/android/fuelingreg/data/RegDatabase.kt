package hu.bme.aut.android.fuelingreg.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hu.bme.aut.android.fuelingreg.data.converters.FuelingTypeConverter
import hu.bme.aut.android.fuelingreg.data.converters.LocalDateConverter
import hu.bme.aut.android.fuelingreg.data.dao.CarDao
import hu.bme.aut.android.fuelingreg.data.dao.FuelingDao
import hu.bme.aut.android.fuelingreg.data.entities.CarEntity
import hu.bme.aut.android.fuelingreg.data.entities.FuelingEntity

@Database(entities = [CarEntity::class, FuelingEntity::class], version = 1)
@TypeConverters(value = [FuelingTypeConverter::class, LocalDateConverter::class])
abstract class RegDatabase : RoomDatabase() {
    abstract fun FuelingDao(): FuelingDao
    abstract fun CarDao(): CarDao
}