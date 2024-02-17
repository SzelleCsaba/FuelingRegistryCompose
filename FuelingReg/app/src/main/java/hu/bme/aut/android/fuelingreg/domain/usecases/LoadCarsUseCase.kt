package hu.bme.aut.android.fuelingreg.domain.usecases

import hu.bme.aut.android.fuelingreg.data.repository.CarRepository
import hu.bme.aut.android.fuelingreg.domain.model.Car
import hu.bme.aut.android.fuelingreg.domain.model.asCar
import kotlinx.coroutines.flow.first
import java.io.IOException

class LoadCarsUseCase(private val repository: CarRepository) {

    suspend operator fun invoke(): Result<List<Car>> {
        return try {
            val cars = repository.getAllCars().first()
            Result.success(cars.map { it.asCar() })
        } catch (e: IOException) {
            Result.failure(e)
        }
    }
}