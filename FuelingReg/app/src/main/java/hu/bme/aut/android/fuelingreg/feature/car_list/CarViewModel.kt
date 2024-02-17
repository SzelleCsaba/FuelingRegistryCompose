package hu.bme.aut.android.fuelingreg.feature.car_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import hu.bme.aut.android.fuelingreg.RegApplication
import hu.bme.aut.android.fuelingreg.domain.usecases.CarUseCases
import hu.bme.aut.android.fuelingreg.domain.usecases.FuelingUseCases
import hu.bme.aut.android.fuelingreg.ui.model.CarUi
import hu.bme.aut.android.fuelingreg.ui.model.asCar
import hu.bme.aut.android.fuelingreg.ui.model.asCarUi
import hu.bme.aut.android.fuelingreg.ui.model.toUiText
import hu.bme.aut.android.fuelingreg.ui.util.UiEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CarViewModel(
    private val carOperations: CarUseCases,
) : ViewModel() {

    private val _state = MutableStateFlow(CarsState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        loadCars()
    }
    private fun loadCars() {

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                CoroutineScope(coroutineContext).launch(Dispatchers.IO) {
                    val cars = carOperations.loadCars().getOrThrow().map { it.asCarUi() }
                    _state.update { it.copy(
                        isLoading = false,
                        cars = cars
                    ) }
                }
            } catch (e: Exception) {
                _state.update {  it.copy(
                    isLoading = false,
                    error = e
                ) }
            }
        }
    }
    fun onDelete(car: CarUi) {
        viewModelScope.launch {
            try {
                carOperations.deleteCar(car.asCar())
                carOperations.deleteCascade(car.asCar().id)
                _uiEvent.send(UiEvent.Success)
            } catch (e: Exception) {
                _uiEvent.send(UiEvent.Failure(e.toUiText()))
            }
        }
        loadCars()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val carOperations = CarUseCases(RegApplication.carRepository)
                CarViewModel(
                    carOperations = carOperations
                )
            }
        }
    }
}