package com.example.booktalk.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.booktalk.ui.screens.BookDetailsScreen
import com.example.booktalk.ui.screens.BookListScreen
import com.example.booktalk.viewModel.MainViewModel

object EndPoints {
    const val ID = "id"
}


@ExperimentalComposeUiApi
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val actions = remember(navController) { MainActions(navController) }
    val context = LocalContext.current

    NavHost(navController, startDestination = Screen.BookList.route) {
        // Home
        composable(Screen.BookList.route) {
            // Create ViewModel manually without Hilt
            val viewModel: MainViewModel = viewModel { MainViewModel() }
            viewModel.getAllBooks(context = context)
            BookListScreen(viewModel, actions)
        }

        // Book Details
        composable(
            "${Screen.Details.route}/{id}",
            arguments = listOf(navArgument(EndPoints.ID) { type = NavType.StringType })
        ) {
            // Create ViewModel manually without Hilt
            val viewModel: MainViewModel = viewModel { MainViewModel() }
            val isbnNo = it.arguments?.getString(EndPoints.ID)
                ?: throw IllegalStateException("'Book ISBN No' shouldn't be null")

            viewModel.getBookByID(context = context, isbnNO = isbnNo)
            BookDetailsScreen(viewModel, actions)
        }
    }
}

class MainActions(navController: NavController) {

    val upPress: () -> Unit = {
        navController.navigateUp()
    }

    val gotoBookDetails: (String) -> Unit = { isbnNo ->
        navController.navigate("${Screen.Details.route}/$isbnNo")
    }

    val gotoBookList: () -> Unit = {
        navController.navigate(Screen.BookList.route)
    }
}


