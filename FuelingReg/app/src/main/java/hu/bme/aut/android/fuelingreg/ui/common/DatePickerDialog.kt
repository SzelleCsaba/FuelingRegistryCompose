package hu.bme.aut.android.fuelingreg.ui.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import com.himanshoe.kalendar.Kalendar
import com.himanshoe.kalendar.model.KalendarType
import hu.bme.aut.android.fuelingreg.R
import kotlinx.datetime.LocalDate

@Composable
fun DatePickerDialog(
    currentDate: LocalDate,
    onConfirm: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedDate by remember { mutableStateOf(currentDate) }
    AlertDialog(
        text = {
            Kalendar(
                onCurrentDayClick = { kalendarDay, _ ->
                    selectedDate = kalendarDay.localDate
                },
                kalendarType = KalendarType.Firey,
                takeMeToDate = currentDate
            )
        },
        confirmButton = {
            Button(onClick = { onConfirm(selectedDate) }) {
                Text(text = stringResource(id = R.string.dialog_ok_button_text))
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(text = stringResource(id = R.string.dialog_dismiss_button_text))
            }
        },
        onDismissRequest = onDismiss
    )
}