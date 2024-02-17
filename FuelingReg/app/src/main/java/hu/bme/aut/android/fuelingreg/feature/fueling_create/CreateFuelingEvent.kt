package hu.bme.aut.android.fuelingreg.feature.fueling_create

import hu.bme.aut.android.fuelingreg.ui.model.TypeUi
import kotlinx.datetime.LocalDate

sealed class CreateFuelingEvent {
    data class ChangeAmount(val value: Double): CreateFuelingEvent()
    data class SelectType(val type: TypeUi): CreateFuelingEvent()
    data class ChangePricePerLiter(val value: Int): CreateFuelingEvent()
    data class ChangeIsFullTank(val value: Boolean): CreateFuelingEvent()
    data class ChangeOdometerAtFueling(val value: Int): CreateFuelingEvent()
    data class SelectDate(val date: LocalDate): CreateFuelingEvent()
    object DeleteFueling: CreateFuelingEvent()
    object SaveFueling: CreateFuelingEvent()
}
