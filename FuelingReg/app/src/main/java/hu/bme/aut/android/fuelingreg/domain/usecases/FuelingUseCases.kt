package hu.bme.aut.android.fuelingreg.domain.usecases

import hu.bme.aut.android.fuelingreg.data.repository.FuelingRepository

class FuelingUseCases(repository: FuelingRepository) {
    val loadFuelings = LoadFuelingsUseCase(repository)
    val loadFuelingsOfACar = LoadFuelingsOfACarUseCase(repository)
    val saveFueling = SaveFuelingUseCase(repository)
    val updateFueling = UpdateFuelingUseCase(repository)
    val deleteFueling = DeleteFuelingUseCase(repository)

}