package com.study.focus.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.study.focus.ui.home.HomeScreen
import com.study.focus.ui.session.SessionScreen
import com.study.focus.ui.dashboard.DashboardScreen
import com.study.focus.ui.onboarding.OnboardingScreen
import com.study.focus.ui.onboarding.checkAccessibilityPermission
import com.study.focus.ui.onboarding.checkOverlayPermission
import com.study.focus.ui.onboarding.checkUsagePermission
import androidx.compose.ui.platform.LocalContext

@Composable
fun FocusAppNavGraph(viewModel: FocusViewModel = viewModel()) {
    val navController = rememberNavController()

    val context = LocalContext.current
    NavHost(navController = navController, startDestination = "onboarding") {
        composable("onboarding") {
            // Auto-advance if all permissions granted
            LaunchedEffect(Unit) {
                if (checkOverlayPermission(context) &&
                    checkUsagePermission(context) &&
                    checkAccessibilityPermission(context)) {
                    navController.navigate("home") {
                        popUpTo("onboarding") { inclusive = true }
                    }
                }
            }

            OnboardingScreen(
                viewModel = viewModel,
                onComplete = {
                    navController.navigate("home") {
                        popUpTo("onboarding") { inclusive = true }
                    }
                }
            )
        }
        composable("home") {
            HomeScreen(
                viewModel = viewModel,
                onStartSession = {
                    navController.navigate("session")
                },
                onOpenDashboard = {
                    navController.navigate("dashboard")
                }
            )
        }
        composable("session") {
            SessionScreen(
                viewModel = viewModel,
                onSessionComplete = {
                    navController.popBackStack("home", inclusive = false)
                }
            )
        }
        composable("dashboard") {
            DashboardScreen(
                viewModel = viewModel,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
