package hu.bme.aut.android.fuelingreg.ui.model

import hu.bme.aut.android.fuelingreg.domain.model.Fueling
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate
import java.time.LocalDateTime

data class FuelingUi (
    val id: Int = 0,
    val amount: String = "",
    val carId: Int = 0,
    val type: TypeUi = TypeUi.StandardGasoline,
    val pricePerLiter: String = "",
    val isFullTank: Boolean = false,
    val odometerAtFueling: String = "",
    val date: String = LocalDate(
        LocalDateTime.now().year,
        LocalDateTime.now().monthValue,
        LocalDateTime.now().dayOfMonth
    ).toString()
)

fun Fueling.asFuelingUi(): FuelingUi = FuelingUi(
    id = id,
    amount = amount.toString(),
    carId = carId,
    type = type.asTypeUi(),
    pricePerLiter = pricePerLiter.toString(),
    isFullTank = isFullTank,
    odometerAtFueling = odometerAtFueling.toString(),
    date = date.toString(),
)

fun FuelingUi.asFueling(_carId: Int): Fueling = Fueling(
    id = id,
    amount = amount.toDouble(),
    carId = _carId,
    type = type.asType(),
    pricePerLiter = pricePerLiter.toInt(),
    isFullTank = isFullTank,
    odometerAtFueling = odometerAtFueling.toInt(),
    date = date.toLocalDate(),
)