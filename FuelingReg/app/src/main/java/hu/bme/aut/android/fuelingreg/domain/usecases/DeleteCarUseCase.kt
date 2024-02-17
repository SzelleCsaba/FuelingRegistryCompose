package hu.bme.aut.android.fuelingreg.domain.usecases

import hu.bme.aut.android.fuelingreg.data.repository.CarRepository
import hu.bme.aut.android.fuelingreg.domain.model.Car
import hu.bme.aut.android.fuelingreg.domain.model.asCarEntity

class DeleteCarUseCase(private val repository: CarRepository) {

    suspend operator fun invoke(car: Car) {
        repository.deleteCar(car.asCarEntity())
    }

}