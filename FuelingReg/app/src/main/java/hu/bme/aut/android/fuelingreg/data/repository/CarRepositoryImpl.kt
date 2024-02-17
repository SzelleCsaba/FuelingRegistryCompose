package hu.bme.aut.android.fuelingreg.data.repository

import hu.bme.aut.android.fuelingreg.data.dao.CarDao
import hu.bme.aut.android.fuelingreg.data.entities.CarEntity
import kotlinx.coroutines.flow.Flow

class CarRepositoryImpl(private val dao: CarDao) : CarRepository {

    override fun getAllCars(): Flow<List<CarEntity>> = dao.getAllCars()

    override suspend fun insertCar(CarEntity: CarEntity) { dao.insertCar(CarEntity) }

    override suspend fun updateCar(CarEntity: CarEntity) { dao.updateCar(CarEntity) }

    override suspend fun deleteCar(CarEntity: CarEntity) { dao.deleteCar(CarEntity) }

    override suspend fun deleteCascade(carId: Int) { dao.deleteCascade(carId) }
}