package hu.bme.aut.android.fuelingreg.navigation

sealed class Screen(val route: String) {
    object Cars: Screen("cars")
    object CreateCar: Screen("createcar")
    object FuelingsOfACar: Screen("fuelings/{id}") {
        fun passId(id: Int) = "fuelings/$id"
    }
    object CreateFueling: Screen ("createfueling/{id}") {
        fun passId(id: Int) = "createfueling/$id"
    }
}
