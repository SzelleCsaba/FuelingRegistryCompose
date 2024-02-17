package hu.bme.aut.android.fuelingreg.domain.usecases

import hu.bme.aut.android.fuelingreg.data.repository.CarRepository

class CarUseCases(repository: CarRepository) {
    val loadCars = LoadCarsUseCase(repository)
    val saveCar = SaveCarUseCase(repository)
    val updateCar = UpdateCarUseCase(repository)
    val deleteCar = DeleteCarUseCase(repository)
    val deleteCascade= DeleteCascadeUseCase(repository)
}