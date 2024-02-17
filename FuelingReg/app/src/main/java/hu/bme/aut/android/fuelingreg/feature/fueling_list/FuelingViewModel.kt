package hu.bme.aut.android.fuelingreg.feature.fueling_list

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import hu.bme.aut.android.fuelingreg.RegApplication
import hu.bme.aut.android.fuelingreg.domain.usecases.FuelingUseCases
import hu.bme.aut.android.fuelingreg.ui.model.FuelingUi
import hu.bme.aut.android.fuelingreg.ui.model.asFueling
import hu.bme.aut.android.fuelingreg.ui.model.asFuelingUi
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

class FuelingViewModel (
    private val savedState: SavedStateHandle,
    private val fuelingOperations: FuelingUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(FuelingsState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        loadFuelings()
    }
    private fun loadFuelings() {

        val carId = checkNotNull<Int>(savedState["id"])
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                CoroutineScope(coroutineContext).launch(Dispatchers.IO) {
                    val fuelings = fuelingOperations.loadFuelingsOfACar(carId).getOrThrow().map { it.asFuelingUi() }
                    _state.update { it.copy(
                        isLoading = false,
                        fuelings = fuelings,
                        carId = carId
                    ) }
                }
                _uiEvent.send(UiEvent.Success)
            } catch (e: Exception) {
                _state.update {  it.copy(
                    isLoading = false,
                    error = e
                ) }
                _uiEvent.send(UiEvent.Failure(e.toUiText()))
            }
        }
    }

    fun onDelete(fueling: FuelingUi) {
        viewModelScope.launch {
            try {
                fuelingOperations.deleteFueling(fueling.asFueling(fueling.carId))
                _uiEvent.send(UiEvent.Success)
            } catch (e: Exception) {
                _uiEvent.send(UiEvent.Failure(e.toUiText()))
            }
        }
        loadFuelings()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val fuelingOperations = FuelingUseCases(RegApplication.fuelingRepository)
                FuelingViewModel(
                    savedState = savedStateHandle,
                    fuelingOperations = fuelingOperations
                )
            }
        }
    }
}