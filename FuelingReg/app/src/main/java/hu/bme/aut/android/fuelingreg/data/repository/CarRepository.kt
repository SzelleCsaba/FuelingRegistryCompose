package hu.bme.aut.android.fuelingreg.data.repository

import hu.bme.aut.android.fuelingreg.data.entities.CarEntity
import kotlinx.coroutines.flow.Flow

interface CarRepository {
    fun getAllCars(): Flow<List<CarEntity>>

    suspend fun insertCar(CarEntity: CarEntity)

    suspend fun updateCar(CarEntity: CarEntity)

    suspend fun deleteCar(CarEntity: CarEntity)

    suspend fun deleteCascade(carId: Int)
}