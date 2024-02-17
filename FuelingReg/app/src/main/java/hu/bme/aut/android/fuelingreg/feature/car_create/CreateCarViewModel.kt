package hu.bme.aut.android.fuelingreg.feature.car_create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import hu.bme.aut.android.fuelingreg.RegApplication
import hu.bme.aut.android.fuelingreg.domain.model.asCarEntity
import hu.bme.aut.android.fuelingreg.domain.usecases.CarUseCases
import hu.bme.aut.android.fuelingreg.ui.model.asCar
import hu.bme.aut.android.fuelingreg.ui.model.toUiText
import hu.bme.aut.android.fuelingreg.ui.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateCarViewModel(
    private val carOperations: CarUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(CreateCarState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: CreateCarEvent) {
        when(event) {
            is CreateCarEvent.ChangeName -> {
                val newValue = event.text
                _state.update { it.copy(
                    car = it.car.copy(name = newValue)
                ) }
            }
            is CreateCarEvent.ChangeDescription -> {
                val newValue = event.text
                _state.update { it.copy(
                    car = it.car.copy(description = newValue)
                ) }
            }
            is CreateCarEvent.ChangeCapacity -> {
                val newValue = event.value.toString()
                _state.update { it.copy(
                    car = it.car.copy(capacity = newValue)
                ) }
            }
            CreateCarEvent.SaveCar -> {
                onSave()
            }
        }
    }

    private fun onSave() {
        viewModelScope.launch {
            try {
                carOperations.saveCar(state.value.car.asCar())
                _uiEvent.send(UiEvent.Success)
            } catch (e: Exception) {
                _uiEvent.send(UiEvent.Failure(e.toUiText()))
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val carOperations = CarUseCases(RegApplication.carRepository)
                CreateCarViewModel(
                    carOperations = carOperations
                )
            }
        }
    }

}