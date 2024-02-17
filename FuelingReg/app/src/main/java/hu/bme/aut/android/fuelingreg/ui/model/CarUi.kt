package hu.bme.aut.android.fuelingreg.ui.model

import hu.bme.aut.android.fuelingreg.domain.model.Car

data class CarUi (
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val capacity: String = ""
)

fun Car.asCarUi(): CarUi = CarUi(
    id = id,
    name = name,
    description = description,
    capacity = capacity.toString()
)

fun CarUi.asCar(): Car = Car(
    id = id,
    name = name,
    description = description,
    capacity = capacity.toInt()
)