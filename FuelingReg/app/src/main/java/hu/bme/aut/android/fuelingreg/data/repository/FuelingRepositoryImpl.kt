package hu.bme.aut.android.fuelingreg.data.repository

import hu.bme.aut.android.fuelingreg.data.dao.FuelingDao
import hu.bme.aut.android.fuelingreg.data.entities.FuelingEntity
import kotlinx.coroutines.flow.Flow

class FuelingRepositoryImpl(private val dao: FuelingDao) : FuelingRepository {

    override fun getAllFuelings(): Flow<List<FuelingEntity>> = dao.getAllFuelings()

    override suspend fun insertFueling(FuelingEntity: FuelingEntity) { dao.insertFueling(FuelingEntity) }

    override suspend fun updateFueling(FuelingEntity: FuelingEntity) { dao.updateFueling(FuelingEntity) }

    override suspend fun deleteFueling(odo: Int, amount: Double, carId: Int) { dao.deleteFueling(odo, amount, carId) }

    override fun getFuelingsOfACar(id: Int): Flow<List<FuelingEntity>> = dao.getFuelingsOfACar(id)

}