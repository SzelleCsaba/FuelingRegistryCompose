package hu.bme.aut.android.fuelingreg.data.repository

import hu.bme.aut.android.fuelingreg.data.entities.FuelingEntity
import kotlinx.coroutines.flow.Flow

interface FuelingRepository {
    fun getAllFuelings(): Flow<List<FuelingEntity>>

    suspend fun insertFueling(FuelingEntity: FuelingEntity)

    suspend fun updateFueling(FuelingEntity: FuelingEntity)

    suspend fun deleteFueling(odo: Int, amount: Double, carId: Int)

    fun getFuelingsOfACar(id: Int): Flow<List<FuelingEntity>>
}