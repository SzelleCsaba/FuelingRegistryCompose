package hu.bme.aut.android.fuelingreg.data.converters

import androidx.room.TypeConverter
import hu.bme.aut.android.fuelingreg.domain.model.Type

object FuelingTypeConverter {

    @TypeConverter
    fun Type.asString(): String = this.name

    @TypeConverter
    fun String.asType(): Type {
        return when(this) {
            Type.stdgasoline.name -> Type.stdgasoline
            Type.stddiesel.name -> Type.stddiesel
            Type.premiumgasoline.name -> Type.premiumgasoline
            Type.premiumdiesel.name -> Type.premiumdiesel
            else -> Type.stdgasoline
        }
    }
}
