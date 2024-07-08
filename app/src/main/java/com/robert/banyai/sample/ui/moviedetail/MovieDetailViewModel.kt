package com.robert.banyai.sample.ui.moviedetail

import androidx.lifecycle.viewModelScope
import com.robert.banyai.sample.domain.usecase.GetMovie
import com.robert.banyai.sample.ui.model.toPresentation
import com.robert.banyai.sample.ui.mvi.BaseViewModel
import com.robert.banyai.sample.ui.moviedetail.MovieDetailContract.StateFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import com.robert.banyai.sample.ui.moviedetail.MovieDetailContract.SideEffect as Effect
import com.robert.banyai.sample.ui.moviedetail.MovieDetailContract.ViewAction as Action
import com.robert.banyai.sample.ui.moviedetail.MovieDetailContract.ViewState as State

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovie: GetMovie,
    stateFactory: StateFactory
) : BaseViewModel<State, Action, Effect>(stateFactory.createInitialState()) {

    init {
        fetchMovie()
    }

    override suspend fun onActionReceived(action: Action) {
        // no-op
    }

    private fun fetchMovie() {
        viewModelScope.launch {
            val movieId = currentState.movieId

            runCatching { getMovie(movieId) }
                .onSuccess { movie ->
                    updateState {
                        copy(
                            movie = movie?.toPresentation()
                        )
                    }
                }
        }
    }
}