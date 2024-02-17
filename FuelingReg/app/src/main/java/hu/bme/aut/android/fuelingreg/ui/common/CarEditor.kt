package hu.bme.aut.android.fuelingreg.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import hu.bme.aut.android.fuelingreg.ui.theme.ShadowColors

@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
fun CarEditor(
    nameValue: String,
    nameOnValueChange: (String) -> Unit,
    descriptionValue: String,
    descriptionOnValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    capacityValue: String,
    capacityOnValueChange: (String) -> Unit,
    enabled: Boolean = true,
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
        ){
            NormalTextField(
                value = nameValue,
                label = stringResource(id = R.string.textfield_label_carname),
                onValueChange = nameOnValueChange,
                singleLine = true,
                onDone = { keyboardController?.hide()  },
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
        ){
            NumberTextField(
                value = capacityValue,
                label = stringResource(id = R.string.textfield_label_carcapacity),
                onValueChange = capacityOnValueChange,
                singleLine = true,
                onDone = { keyboardController?.hide() },
                modifier = Modifier.fillMaxWidth(0.98f).fillMaxHeight(0.98f),
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
        ){
            NormalTextField(
                value = descriptionValue,
                label = stringResource(id = R.string.textfield_label_cardescription),
                onValueChange = descriptionOnValueChange,
                singleLine = true,
                onDone = { keyboardController?.hide() },
                modifier = Modifier.fillMaxWidth(0.98f).fillMaxHeight(0.98f),

            )
        }
    }
}

@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
@Preview(showBackground = true)
fun CarEditor_Preview() {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var capacity by remember { mutableStateOf("") }

    Box(Modifier.fillMaxSize()) {
        CarEditor(
            nameValue = name,
            nameOnValueChange = { name = it },
            descriptionValue = description,
            descriptionOnValueChange = { description = it },
            capacityValue = capacity,
            capacityOnValueChange = { capacity = it },
        )
    }
}