package hu.bme.aut.android.fuelingreg.feature.fueling_create


import androidx.compose.foundation.layout.*
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
import hu.bme.aut.android.fuelingreg.ui.common.DatePickerDialog
import hu.bme.aut.android.fuelingreg.ui.common.FuelingEditor
import hu.bme.aut.android.fuelingreg.ui.common.RegAppBar
import hu.bme.aut.android.fuelingreg.ui.util.UiEvent
import kotlinx.coroutines.launch
import kotlinx.datetime.toLocalDate
import kotlin.math.roundToInt

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalMaterial3Api
@Composable
fun CreateFuelingScreen (
    onNavigateBack: (Int) -> Unit,
    viewModel: CreateFuelingViewModel = viewModel(factory = CreateFuelingViewModel.Factory)
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    var showDialog by remember { mutableStateOf(false) }
    val hostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.Success -> { onNavigateBack(viewModel.carId) }
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
                title = stringResource(id = R.string.app_bar_title_create_fueling),
                onNavigateBack = { onNavigateBack(viewModel.carId) },
                actions = { }
            )
        },
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = { viewModel.onEvent(CreateFuelingEvent.SaveFueling) },
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
            FuelingEditor(
                amountValue = state.fueling.amount,
                amountValueChange = { viewModel.onEvent(CreateFuelingEvent.ChangeAmount(if (it=="") 0.0 else it.toDouble())) },
                pricePerLiterValue = state.fueling.pricePerLiter,
                pricePerLiterOnValueChange = { viewModel.onEvent(CreateFuelingEvent.ChangePricePerLiter(if (it=="") 0 else it.toInt())) },
                isFullTankValue = state.fueling.isFullTank,
                isFullTankOnValueChange = { viewModel.onEvent(CreateFuelingEvent.ChangeIsFullTank(it)) },
                odometerAtFuelingValue = state.fueling.odometerAtFueling,
                odometerAtFuelingOnValueChange = { viewModel.onEvent(CreateFuelingEvent.ChangeOdometerAtFueling(if (it=="") 0 else it.toInt())) },
                selectedType = state.fueling.type,
                onTypeSelected = { viewModel.onEvent(CreateFuelingEvent.SelectType(it)) },
                pickedDate = state.fueling.date.toLocalDate(),
                onDatePickerClicked = {
                    showDialog = true
                },
                modifier = Modifier
            )
            if (showDialog) {
                DatePickerDialog(
                    currentDate = state.fueling.date.toLocalDate(),
                    onConfirm = { date ->
                        viewModel.onEvent(CreateFuelingEvent.SelectDate(date))
                        showDialog = false
                    },
                    onDismiss = {
                        showDialog = false
                    }
                )
            }
        }
    }
}