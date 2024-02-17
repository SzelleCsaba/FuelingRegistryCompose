package hu.bme.aut.android.fuelingreg.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gandiva.neumorphic.LightSource
import com.gandiva.neumorphic.neu
import com.gandiva.neumorphic.shape.Pressed
import com.gandiva.neumorphic.shape.RoundedCorner
import hu.bme.aut.android.fuelingreg.R
import hu.bme.aut.android.fuelingreg.domain.model.Type
import hu.bme.aut.android.fuelingreg.ui.model.TypeUi
import hu.bme.aut.android.fuelingreg.ui.model.asTypeUi
import hu.bme.aut.android.fuelingreg.ui.theme.ShadowColors
import kotlinx.datetime.LocalDate
import java.time.LocalDateTime

@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
fun FuelingEditor(
    amountValue: String,
    amountValueChange: (String) -> Unit,
    pricePerLiterValue: String,
    pricePerLiterOnValueChange: (String) -> Unit,
    isFullTankValue: Boolean,
    isFullTankOnValueChange: (Boolean) -> Unit,
    odometerAtFuelingValue: String,
    odometerAtFuelingOnValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    types: List<TypeUi> = Type.types.map { it.asTypeUi() },
    selectedType: TypeUi,
    onTypeSelected: (TypeUi) -> Unit,
    pickedDate: LocalDate,
    onDatePickerClicked: () -> Unit,
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(0.98f)
                .height(100.dp)
                .neu(
                    lightShadowColor = ShadowColors.lightShadow(),
                    darkShadowColor = ShadowColors.darkShadow(),
                    shadowElevation = 6.dp,
                    lightSource = LightSource.LEFT_TOP,
                    shape = Pressed(RoundedCorner(24.dp)),
                ),
            shape = RoundedCornerShape(24.dp),
        ) {
            NumberTextField(
                value = amountValue,
                label = stringResource(id = R.string.textfield_label_fuelingAmount),
                onValueChange = amountValueChange,
                singleLine = false,
                onDone = { keyboardController?.hide() },
                modifier = Modifier.fillMaxWidth(0.98f).fillMaxHeight(0.98f)
            )
        }
        Card(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(0.98f)
                .height(100.dp)
                .neu(
                    lightShadowColor = ShadowColors.lightShadow(),
                    darkShadowColor = ShadowColors.darkShadow(),
                    shadowElevation = 6.dp,
                    lightSource = LightSource.LEFT_TOP,
                    shape = Pressed(RoundedCorner(24.dp)),
                ),
            shape = RoundedCornerShape(24.dp),
        ) {
            NumberTextField(
                value = pricePerLiterValue,
                label = stringResource(id = R.string.textfield_label_pricePerLiter),
                onValueChange = pricePerLiterOnValueChange,
                singleLine = false,
                onDone = { keyboardController?.hide() },
                modifier = Modifier.fillMaxWidth(0.98f).fillMaxHeight(0.98f)
            )
        }
        Card(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(0.98f)
                .height(100.dp)
                .neu(
                    lightShadowColor = ShadowColors.lightShadow(),
                    darkShadowColor = ShadowColors.darkShadow(),
                    shadowElevation = 6.dp,
                    lightSource = LightSource.LEFT_TOP,
                    shape = Pressed(RoundedCorner(24.dp)),
                ),
            shape = RoundedCornerShape(24.dp),
        ) {
            NumberTextField(
                value = odometerAtFuelingValue,
                label = stringResource(id = R.string.textfield_label_odoAtFueling),
                onValueChange = odometerAtFuelingOnValueChange,
                singleLine = false,
                onDone = { keyboardController?.hide() },
                modifier = Modifier.fillMaxWidth(0.98f).fillMaxHeight(0.98f)
            )
        }

        CheckBoxWithLabel(
            checked = isFullTankValue,
            label = stringResource(id = R.string.textfield_label_isFullTank),
            onCheckedChange = isFullTankOnValueChange,
            modifier = Modifier.height(75.dp),
        )

        TypeDropDown(
            types = types,
            selectedType = selectedType,
            onTypeSelected = onTypeSelected,
            modifier = Modifier.height(75.dp).fillMaxWidth(0.98f),
        )
        DatePicker(
            pickedDate = pickedDate,
            onClick = onDatePickerClicked,
            modifier = Modifier.height(75.dp).fillMaxWidth(0.98f),
        )
    }
}

@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
@Preview(showBackground = true)
fun FuelingEditor_Preview() {
    var amount by remember { mutableStateOf("") }
    var pricePerLiter by remember { mutableStateOf("") }
    var isFullTank by remember { mutableStateOf(false) }
    var odometerAtFueling by remember { mutableStateOf("") }

    val types = listOf(TypeUi.StandardGasoline, TypeUi.PremiumGasoline, TypeUi.StandardDiesel, TypeUi.PremiumDiesel)
    var selectedType by remember { mutableStateOf(types[0]) }

    val c = LocalDateTime.now()
    var pickedDate by remember { mutableStateOf(LocalDate(c.year,c.month,c.dayOfMonth)) }

    Box(Modifier.fillMaxSize()) {
        FuelingEditor(
            amountValue = amount,
            amountValueChange = { amount = it },
            pricePerLiterValue = pricePerLiter,
            pricePerLiterOnValueChange = { pricePerLiter = it },
            isFullTankValue = isFullTank,
            isFullTankOnValueChange = { isFullTank = it },
            odometerAtFuelingValue = odometerAtFueling,
            odometerAtFuelingOnValueChange = { odometerAtFueling = it },
            types = types,
            selectedType = selectedType,
            onTypeSelected = { selectedType = it },
            pickedDate = pickedDate,
            onDatePickerClicked = {

            },
        )
        /*
        DatePickerDialog(
            currentDate = LocalDate(c.year, c.month, c.dayOfMonth),
            onConfirm = { pickedDate = it },
            onDismiss = {

            }
        )*/
    }
}