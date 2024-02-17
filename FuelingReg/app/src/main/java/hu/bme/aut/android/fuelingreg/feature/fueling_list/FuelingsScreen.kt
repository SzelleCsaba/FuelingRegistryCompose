package hu.bme.aut.android.fuelingreg.feature.fueling_list

import android.content.Intent
import android.os.Bundle
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gandiva.neumorphic.LightSource
import com.gandiva.neumorphic.neu
import com.gandiva.neumorphic.shape.Flat
import com.gandiva.neumorphic.shape.RoundedCorner
import hu.bme.aut.android.fuelingreg.R
import hu.bme.aut.android.fuelingreg.ui.common.RegAppBar
import hu.bme.aut.android.fuelingreg.ui.model.toUiText
import hu.bme.aut.android.fuelingreg.ui.theme.ShadowColors
import hu.bme.aut.android.fuelingreg.ui.util.UiEvent
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@OptIn(ExperimentalFoundationApi::class)
@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
fun FuelingsScreen (
    onNavigateBack: () -> Unit,
    onFabClick: (Int) -> Unit,
    viewModel: FuelingViewModel = viewModel(factory = FuelingViewModel.Factory)
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    val hostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.Success -> { /*onNavigateBack()*/ }
                is UiEvent.Failure -> {
                    scope.launch {
                        hostState.showSnackbar(uiEvent.message.asString(context))
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            RegAppBar(
                title = stringResource(id = hu.bme.aut.android.fuelingreg.R.string.app_bar_title_fueling_list),
                onNavigateBack = onNavigateBack,
                actions = { }
            )
        },
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = { onFabClick(state.carId) },
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                )
            } else if (state.isError) {
                Text(
                    text = state.error?.toUiText()?.asString(context)
                        ?: stringResource(id = R.string.some_error_message)
                )
            } else {
                if (state.fuelings.isEmpty()) {
                    Text(text = stringResource(id = R.string.text_empty_fueling_list))
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(0.98f)
                            .padding(it)
                            .clip(RoundedCornerShape(5.dp))
                    ) {
                        items(state.fuelings.size) { i ->
                            Card(modifier = Modifier
                                .padding(15.dp)
                                .neu(
                                    lightShadowColor = ShadowColors.lightShadow(),
                                    darkShadowColor = ShadowColors.darkShadow(),
                                    shadowElevation = 6.dp,
                                    lightSource = LightSource.LEFT_TOP,
                                    shape = Flat(RoundedCorner(24.dp)),
                                ),
                                shape = RoundedCornerShape(24.dp),
                            ) {
                                ListItem(
                                    headlineText = {
                                        Text(
                                            text = stringResource(
                                                id = R.string.list_item_date_text,
                                                state.fuelings[i].date
                                            )
                                        )
                                    },
                                    supportingText = {
                                        Column(verticalArrangement = Arrangement.SpaceAround) {
                                            Text(text = stringResource(
                                                id = R.string.list_item_odo_text,
                                                state.fuelings[i].odometerAtFueling
                                            ))
                                            Text(text = stringResource(
                                                id = R.string.list_item_amount_text,
                                                state.fuelings[i].amount
                                            ))
                                            Text(text = stringResource(
                                                id = R.string.list_item_price_text,
                                                state.fuelings[i].pricePerLiter
                                            ))
                                            Text(text = stringResource(
                                                id = R.string.list_item_sumprice_text,
                                                (state.fuelings[i].pricePerLiter.toInt()*state.fuelings[i].amount.toDouble()).roundToInt().toString()
                                            ))
                                            Text(text = stringResource(id = state.fuelings[i].type.title))
                                            if (state.fuelings[i].isFullTank && i > 0){
                                                val consumption: Double = (state.fuelings[i].amount.toDouble()/(state.fuelings[i].odometerAtFueling.toInt() -state.fuelings[i-1].odometerAtFueling.toInt())*100*100).roundToInt()/100.0
                                                Text(text = stringResource(
                                                    id = R.string.list_item_consumption_text,
                                                    consumption.toString()
                                                ))
                                            }

                                        }
                                    },
                                    modifier = Modifier
                                        .combinedClickable(
                                            onClick = {
                                                val sendIntent: Intent = Intent().apply {
                                                    action = Intent.ACTION_SEND
                                                    putExtra(
                                                        Intent.EXTRA_TEXT,
                                                        "${state.fuelings[i].date} Napon ${state.fuelings[i].amount}L üzemanyagot tankoltam ${state.fuelings[i].pricePerLiter}Ft/L egységáron.",
                                                    )
                                                    type = "text/plain"
                                                }

                                                val shareIntent =
                                                    Intent.createChooser(sendIntent, null)
                                                ContextCompat.startActivity(
                                                    context,
                                                    shareIntent,
                                                    Bundle()
                                                )
                                            },
                                            onLongClick = { viewModel.onDelete(state.fuelings[i]) }
                                        )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}