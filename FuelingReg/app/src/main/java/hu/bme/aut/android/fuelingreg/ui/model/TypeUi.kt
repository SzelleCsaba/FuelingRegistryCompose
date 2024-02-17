package hu.bme.aut.android.fuelingreg.ui.model
import hu.bme.aut.android.fuelingreg.R
import hu.bme.aut.android.fuelingreg.domain.model.Type

sealed class TypeUi(
    val title: Int,
) {

    object StandardGasoline: TypeUi(
        title = R.string.standard_gasoline,
    )
    object PremiumGasoline: TypeUi(
        title = R.string.premium_gasoline,
    )
    object StandardDiesel: TypeUi(
        title = R.string.standard_diesel,
    )
    object PremiumDiesel: TypeUi(
        title = R.string.premium_diesel,
    )
}

fun TypeUi.asType(): Type {
    return when(this) {
        is TypeUi.StandardGasoline -> Type.stdgasoline
        is TypeUi.PremiumGasoline -> Type.premiumgasoline
        is TypeUi.StandardDiesel -> Type.stddiesel
        is TypeUi.PremiumDiesel -> Type.premiumdiesel
    }
}

fun Type.asTypeUi(): TypeUi {
    return when(this) {
        Type.stdgasoline -> TypeUi.StandardGasoline
        Type.premiumgasoline -> TypeUi.PremiumGasoline
        Type.stddiesel -> TypeUi.StandardDiesel
        Type.premiumdiesel -> TypeUi.PremiumDiesel
    }
}