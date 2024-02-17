package hu.bme.aut.android.fuelingreg.feature.fueling_list

import hu.bme.aut.android.fuelingreg.ui.model.FuelingUi

data class FuelingsState (
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val isError: Boolean = error != null,
    val fuelings: List<FuelingUi> = emptyList(),
    val carId: Int = 0
)