package hu.bme.aut.android.fuelingreg.data.dao

import androidx.room.*
import hu.bme.aut.android.fuelingreg.data.entities.CarEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CarDao {
    @Query("SELECT * FROM car_table")
    fun getAllCars(): Flow<List<CarEntity>>

    @Insert
    suspend fun insertCar(CarEntity: CarEntity)

    @Update
    suspend fun updateCar(CarEntity: CarEntity)

    @Delete
    suspend fun deleteCar(CarEntity: CarEntity)

    @Query("DELETE FROM fueling_table WHERE carId = :carId")
    suspend fun deleteCascade(carId: Int)
}