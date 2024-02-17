package hu.bme.aut.android.fuelingreg

import android.app.Application
import androidx.room.Room
import hu.bme.aut.android.fuelingreg.data.RegDatabase
import hu.bme.aut.android.fuelingreg.data.repository.CarRepositoryImpl
import hu.bme.aut.android.fuelingreg.data.repository.FuelingRepositoryImpl

class RegApplication: Application() {

    companion object {
        private lateinit var db: RegDatabase

        lateinit var carRepository: CarRepositoryImpl
        lateinit var fuelingRepository: FuelingRepositoryImpl
    }

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            RegDatabase::class.java,
            "car_database"
        ).fallbackToDestructiveMigration().build()

        carRepository = CarRepositoryImpl(db.CarDao())
        fuelingRepository = FuelingRepositoryImpl(db.FuelingDao())
    }
}