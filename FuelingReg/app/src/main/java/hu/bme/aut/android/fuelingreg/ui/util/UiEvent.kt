package hu.bme.aut.android.fuelingreg.ui.util

import hu.bme.aut.android.fuelingreg.ui.model.UiText

sealed class UiEvent {
    object Success: UiEvent()
    data class Failure(val message: UiText): UiEvent()
}