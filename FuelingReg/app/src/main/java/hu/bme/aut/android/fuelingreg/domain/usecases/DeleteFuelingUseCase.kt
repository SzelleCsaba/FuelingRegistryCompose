package hu.bme.aut.android.fuelingreg.domain.usecases

import hu.bme.aut.android.fuelingreg.data.entities.FuelingEntity
import hu.bme.aut.android.fuelingreg.data.repository.FuelingRepository
import hu.bme.aut.android.fuelingreg.domain.model.Fueling
import hu.bme.aut.android.fuelingreg.domain.model.asFuelingEntity

class DeleteFuelingUseCase (private val repository: FuelingRepository) {

    suspend operator fun invoke(fueling: Fueling) {
        repository.deleteFueling(fueling.asFuelingEntity().odometerAtFueling, fueling.asFuelingEntity().amount, fueling.asFuelingEntity().carId)
    }

}