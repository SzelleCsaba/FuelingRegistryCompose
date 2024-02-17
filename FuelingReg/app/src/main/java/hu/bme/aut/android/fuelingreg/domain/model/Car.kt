package hu.bme.aut.android.fuelingreg.domain.model

import hu.bme.aut.android.fuelingreg.data.entities.CarEntity

data class Car(
    val id: Int,
    val name: String,
    val description: String,
    val capacity: Int
)

fun CarEntity.asCar(): Car = Car(
    id = id,
    name = name,
    description = description,
    capacity = capacity
)

fun Car.asCarEntity(): CarEntity = CarEntity(
    id = id,
    name = name,
    description = description,
    capacity = capacity
)
