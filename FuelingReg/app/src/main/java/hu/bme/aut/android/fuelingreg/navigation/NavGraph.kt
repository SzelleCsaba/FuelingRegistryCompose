package hu.bme.aut.android.fuelingreg.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hu.bme.aut.android.fuelingreg.feature.car_create.CreateCarScreen
import hu.bme.aut.android.fuelingreg.feature.car_list.CarsScreen
import hu.bme.aut.android.fuelingreg.feature.fueling_create.CreateFuelingScreen
import hu.bme.aut.android.fuelingreg.feature.fueling_list.FuelingsScreen

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Cars.route
    ) {
        composable(Screen.Cars.route) {
            CarsScreen(
                onListItemClick = {
                    navController.navigate(Screen.FuelingsOfACar.passId(it))
                },
                onFabClick = {
                    navController.navigate(Screen.CreateCar.route)
                }
            )
        }

        composable(Screen.CreateCar.route) {
            CreateCarScreen(
                onNavigateBack = {
                    navController.popBackStack(
                        route = Screen.Cars.route,
                        inclusive = true
                    )
                    navController.navigate(Screen.Cars.route)
                }
            )
        }
        composable(
            route = Screen.FuelingsOfACar.route,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) {
            FuelingsScreen(
                onFabClick = {
                    navController.navigate(Screen.CreateFueling.passId(it))
                },
                onNavigateBack = {
                    navController.popBackStack(
                        route = Screen.Cars.route,
                        inclusive = true
                    )
                    navController.navigate(Screen.Cars.route)
                }
            )
        }
        composable(
            route = Screen.CreateFueling.route,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) {
            CreateFuelingScreen(
                onNavigateBack = {
                    navController.popBackStack(
                        route = Screen.FuelingsOfACar.passId(it),
                        inclusive = true
                    )
                    navController.navigate(Screen.FuelingsOfACar.passId(it))
                }
            )
        }
    }
}