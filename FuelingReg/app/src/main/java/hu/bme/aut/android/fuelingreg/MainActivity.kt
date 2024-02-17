package hu.bme.aut.android.fuelingreg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import hu.bme.aut.android.fuelingreg.navigation.NavGraph
import hu.bme.aut.android.fuelingreg.ui.theme.FuelingRegTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FuelingRegTheme {
                NavGraph()
            }
        }
    }
}