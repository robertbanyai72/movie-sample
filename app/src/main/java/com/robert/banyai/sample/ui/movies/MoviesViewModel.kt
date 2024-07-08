package com.robert.banyai.sample.ui.movies

import androidx.lifecycle.viewModelScope
import com.robert.banyai.sample.domain.usecase.GetTopRatedMovies
import com.robert.banyai.sample.ui.model.toPresentation
import com.robert.banyai.sample.ui.movies.MoviesContract.StateFactory
import com.robert.banyai.sample.ui.mvi.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import com.robert.banyai.sample.ui.movies.MoviesContract.SideEffect as Effect
import com.robert.banyai.sample.ui.movies.MoviesContract.ViewAction as Action
import com.robert.banyai.sample.ui.movies.MoviesContract.ViewState as State

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getTopRatedMovies: GetTopRatedMovies,
    stateFactory: StateFactory
) : BaseViewModel<State, Action, Effect>(stateFactory.createInitialState()) {

    init {
        fetchMovies()
    }

    override suspend fun onActionReceived(action: Action) {
        when (action) {
            is Action.OpenDetail -> {
                sideEffect(Effect.OpenDetail(action.movieId))
            }
        }
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            runCatching { getTopRatedMovies() }
                .onSuccess { movies ->
                    updateState {
                        copy(
                            movies = movies.map { it.toPresentation() }
                        )
                    }
                }
        }
    }
}