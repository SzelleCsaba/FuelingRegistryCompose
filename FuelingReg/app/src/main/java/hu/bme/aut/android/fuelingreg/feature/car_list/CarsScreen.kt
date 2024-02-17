package hu.bme.aut.android.fuelingreg.feature.car_list


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gandiva.neumorphic.LightSource
import com.gandiva.neumorphic.neu
import com.gandiva.neumorphic.shape.Flat
import com.gandiva.neumorphic.shape.RoundedCorner
import hu.bme.aut.android.fuelingreg.R
import hu.bme.aut.android.fuelingreg.ui.model.toUiText
import hu.bme.aut.android.fuelingreg.ui.theme.ShadowColors

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalMaterial3Api
@Composable
fun CarsScreen(
    onListItemClick: (Int) -> Unit,
    onFabClick: () -> Unit,
    viewModel: CarViewModel = viewModel(factory = CarViewModel.Factory),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier,
                title = { Text(text = stringResource(id = R.string.app_bar_title_cars)) },
                actions = {
                },
            )
        },
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = onFabClick
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
                CircularProgressIndicator()
            } else if (state.isError) {
                Text(
                    text = state.error?.toUiText()?.asString(context)
                        ?: stringResource(id = R.string.some_error_message)
                )
            } else {
                if (state.cars.isEmpty()) {
                    Text(text = stringResource(id = R.string.text_empty_car_list))
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(0.98f)
                            .padding(it)
                            .clip(RoundedCornerShape(5.dp))
                    ) {
                        items(state.cars.size) { i ->
                            Card(modifier = Modifier.padding(15.dp)
                                .neu(
                                    lightShadowColor = ShadowColors.lightShadow(),
                                    darkShadowColor = ShadowColors.darkShadow(),
                                    shadowElevation = 6.dp,
                                    lightSource = LightSource.LEFT_TOP,
                                    shape = Flat(RoundedCorner(24.dp)),
                                ),
                                shape = RoundedCornerShape(24.dp),

                            ){
                            ListItem(
                                headlineText = {
                                    Text(text = state.cars[i].name)
                                },
                                supportingText = {
                                    Column(verticalArrangement = Arrangement.SpaceAround) {
                                        Text(
                                            text = stringResource(
                                                id = R.string.list_item_supporting_text,
                                                state.cars[i].capacity
                                            )
                                        )
                                        Text(text = state.cars[i].description)
                                    }
                                },
                                modifier = Modifier
                                    .combinedClickable(
                                        onClick = { onListItemClick(state.cars[i].id) },
                                        onLongClick = {viewModel.onDelete(state.cars[i])}
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