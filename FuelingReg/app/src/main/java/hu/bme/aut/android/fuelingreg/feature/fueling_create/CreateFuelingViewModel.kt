package hu.bme.aut.android.fuelingreg.feature.fueling_create

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import hu.bme.aut.android.fuelingreg.RegApplication
import hu.bme.aut.android.fuelingreg.domain.usecases.FuelingUseCases
import hu.bme.aut.android.fuelingreg.ui.model.asFueling
import hu.bme.aut.android.fuelingreg.ui.model.toUiText
import hu.bme.aut.android.fuelingreg.ui.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateFuelingViewModel(
    private val savedState: SavedStateHandle,
    private val fuelingOperations: FuelingUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(CreateFuelingState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    val carId = checkNotNull<Int>(savedState["id"])

    fun onEvent(event: CreateFuelingEvent) {

        when(event) {
            is CreateFuelingEvent.ChangeAmount -> {
                val newValue = event.value.toString()
                _state.update { it.copy(
                    fueling = it.fueling.copy(amount = newValue)
                ) }
            }
            is CreateFuelingEvent.SelectType -> {
                val newValue = event.type
                _state.update { it.copy(
                    fueling = it.fueling.copy(type = newValue)
                ) }
            }
            is CreateFuelingEvent.ChangePricePerLiter -> {
                val newValue = event.value.toString()
                _state.update { it.copy(
                    fueling = it.fueling.copy(pricePerLiter = newValue)
                ) }
            }
            is CreateFuelingEvent.ChangeIsFullTank -> {
                val newValue = event.value
                _state.update { it.copy(
                    fueling = it.fueling.copy(isFullTank = newValue)
                ) }
            }
            is CreateFuelingEvent.ChangeOdometerAtFueling -> {
                val newValue = event.value.toString()
                _state.update { it.copy(
                    fueling = it.fueling.copy(odometerAtFueling = newValue)
                ) }
            }
            is CreateFuelingEvent.SelectDate -> {
                val newValue = event.date
                _state.update { it.copy(
                    fueling = it.fueling.copy(date = newValue.toString())
                ) }
            }
            CreateFuelingEvent.SaveFueling -> {
                onSave()
            }
            CreateFuelingEvent.DeleteFueling -> {
                onDelete()
            }
        }
    }

    private fun onSave() {
        viewModelScope.launch {
            try {
                fuelingOperations.saveFueling(state.value.fueling.asFueling(carId))
                _uiEvent.send(UiEvent.Success)
            } catch (e: Exception) {
                _uiEvent.send(UiEvent.Failure(e.toUiText()))
            }
        }
    }

    private fun onDelete() {
        viewModelScope.launch {
            try {
                fuelingOperations.deleteFueling(state.value.fueling.asFueling(carId))
                _uiEvent.send(UiEvent.Success)
            } catch (e: Exception) {
                _uiEvent.send(UiEvent.Failure(e.toUiText()))
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val fuelingOperations = FuelingUseCases(RegApplication.fuelingRepository)
                CreateFuelingViewModel(
                    savedState = savedStateHandle,
                    fuelingOperations = fuelingOperations
                )
            }
        }
    }

}