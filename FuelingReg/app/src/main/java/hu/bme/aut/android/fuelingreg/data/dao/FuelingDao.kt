package hu.bme.aut.android.fuelingreg.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import hu.bme.aut.android.fuelingreg.data.entities.FuelingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FuelingDao {
    @Query("SELECT * FROM fueling_table")
    fun getAllFuelings(): Flow<List<FuelingEntity>>

    @Insert
    suspend fun insertFueling(FuelingEntity: FuelingEntity)

    @Update
    suspend fun updateFueling(FuelingEntity: FuelingEntity)

    @Query("DELETE FROM fueling_table WHERE odometerAtFueling = :odo AND amount = :amount AND carId = :carId")
    suspend fun deleteFueling(odo: Int, amount: Double, carId: Int)

    @Query("SELECT * FROM fueling_table INNER JOIN car_table ON  car_table.id =fueling_table.carId WHERE carId = :id ORDER BY odometerAtFueling ")
    fun getFuelingsOfACar(id: Int): Flow<List<FuelingEntity>>
}