package hu.bme.aut.android.fuelingreg.domain.usecases

import hu.bme.aut.android.fuelingreg.data.repository.FuelingRepository
import hu.bme.aut.android.fuelingreg.domain.model.Fueling
import hu.bme.aut.android.fuelingreg.domain.model.asFueling
import kotlinx.coroutines.flow.first
import java.io.IOException

class LoadFuelingsOfACarUseCase(private val repository: FuelingRepository) {

    suspend operator fun invoke(carId: Int): Result<List<Fueling>> {
        return try {
            val fuelings = repository.getFuelingsOfACar(carId).first()
            Result.success(fuelings.map { it.asFueling() })
        } catch (e: IOException) {
            Result.failure(e)
        }
    }
}