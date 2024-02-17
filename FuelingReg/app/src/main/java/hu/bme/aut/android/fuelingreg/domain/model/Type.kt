package hu.bme.aut.android.fuelingreg.domain.model

enum class Type {
    stdgasoline,
    stddiesel,
    premiumgasoline,
    premiumdiesel;

    companion object {
        val types = listOf(stdgasoline, stddiesel, premiumgasoline, premiumdiesel)
    }
}