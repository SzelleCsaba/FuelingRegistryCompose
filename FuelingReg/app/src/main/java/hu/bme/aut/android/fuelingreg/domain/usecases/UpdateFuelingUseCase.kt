package hu.bme.aut.android.fuelingreg.domain.usecases

import hu.bme.aut.android.fuelingreg.data.repository.FuelingRepository
import hu.bme.aut.android.fuelingreg.domain.model.Fueling
import hu.bme.aut.android.fuelingreg.domain.model.asFuelingEntity

class UpdateFuelingUseCase (private val repository: FuelingRepository) {

    suspend operator fun invoke(fueling: Fueling) {
        repository.updateFueling(fueling.asFuelingEntity())
    }

}