package hu.bme.aut.android.fuelingreg.domain.usecases

import hu.bme.aut.android.fuelingreg.data.repository.CarRepository

class DeleteCascadeUseCase(private val repository: CarRepository) {

    suspend operator fun invoke(id: Int) {
        repository.deleteCascade(id)
    }

}