package hu.bme.aut.android.fuelingreg.feature.car_create

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.bme.aut.android.fuelingreg.R
import hu.bme.aut.android.fuelingreg.ui.common.CarEditor
import hu.bme.aut.android.fuelingreg.ui.common.RegAppBar
import hu.bme.aut.android.fuelingreg.ui.util.UiEvent
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
fun CreateCarScreen(
    onNavigateBack: () -> Unit,
    viewModel: CreateCarViewModel = viewModel(factory = CreateCarViewModel.Factory)
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val hostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            when(uiEvent) {
                is UiEvent.Success -> { onNavigateBack() }
                is UiEvent.Failure -> {
                    scope.launch {
                        hostState.showSnackbar(uiEvent.message.asString(context))
                    }
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState) },
        topBar = {
            RegAppBar(
                title = stringResource(id = R.string.app_bar_title_create_car),
                onNavigateBack = onNavigateBack,
                actions = { }
            )
        },
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = { viewModel.onEvent(CreateCarEvent.SaveCar) },
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = null)
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            CarEditor(
                nameValue = state.car.name,
                nameOnValueChange = { viewModel.onEvent(CreateCarEvent.ChangeName(it)) },
                descriptionValue = state.car.description,
                descriptionOnValueChange = { viewModel.onEvent(CreateCarEvent.ChangeDescription(it)) },
                capacityValue = state.car.capacity,
                capacityOnValueChange = { viewModel.onEvent(CreateCarEvent.ChangeCapacity(if (it=="") 0 else it.toDouble().roundToInt())) },
                modifier = Modifier
            )
        }
    }
}
