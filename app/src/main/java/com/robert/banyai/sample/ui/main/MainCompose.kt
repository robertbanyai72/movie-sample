package com.robert.banyai.sample.ui.main

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.robert.banyai.sample.ui.moviedetail.MovieDetailCompose
import com.robert.banyai.sample.ui.moviedetail.MovieDetailViewModel
import com.robert.banyai.sample.ui.movies.MoviesScreen
import com.robert.banyai.sample.ui.movies.MoviesViewModel

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val startRoute = "movies"

    NavHost(
        navController = navController,
        startDestination = startRoute
    ) {
        composable("movies") { _ ->
            val viewModel = hiltViewModel<MoviesViewModel>()

            MoviesScreen(viewModel = viewModel) { moveId ->
                navController.navigate(route = "movieDetail/${moveId}")
            }
        }

        composable(
            route = "movieDetail/{movieId}",
            arguments = listOf(
                navArgument(name = "movieId") { type = NavType.IntType }
            )
        ) { _ ->
            val viewModel = hiltViewModel<MovieDetailViewModel>()
            MovieDetailCompose(viewModel = viewModel)
        }
    }
}