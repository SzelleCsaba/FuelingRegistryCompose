package hu.bme.aut.android.fuelingreg.feature.car_list

import hu.bme.aut.android.fuelingreg.ui.model.CarUi

data class CarsState (
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val isError: Boolean = error != null,
    val cars: List<CarUi> = emptyList()
)