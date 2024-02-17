package hu.bme.aut.android.fuelingreg.feature.car_create

sealed class CreateCarEvent {
    data class ChangeName(val text: String): CreateCarEvent()
    data class ChangeDescription(val text: String): CreateCarEvent()
    data class ChangeCapacity(val value: Int): CreateCarEvent()
    object SaveCar: CreateCarEvent()
}