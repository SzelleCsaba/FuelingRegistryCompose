package hu.bme.aut.android.fuelingreg.domain.model

import hu.bme.aut.android.fuelingreg.data.entities.FuelingEntity
import kotlinx.datetime.LocalDate

data class Fueling(
    val id: Int,
    val amount: Double,
    val carId: Int,
    val type: Type,
    val pricePerLiter: Int,
    val isFullTank: Boolean,
    val odometerAtFueling: Int,
    val date: LocalDate,
)

fun FuelingEntity.asFueling(): Fueling = Fueling(
    id = id,
    amount = amount,
    carId = carId,
    type = type,
    pricePerLiter = pricePerLiter,
    isFullTank = isFullTank,
    odometerAtFueling = odometerAtFueling,
    date = date,
)

fun Fueling.asFuelingEntity(): FuelingEntity = FuelingEntity(
    id = id,
    amount = amount,
    carId = carId,
    type = type,
    pricePerLiter = pricePerLiter,
    isFullTank = isFullTank,
    odometerAtFueling = odometerAtFueling,
    date = date,
)