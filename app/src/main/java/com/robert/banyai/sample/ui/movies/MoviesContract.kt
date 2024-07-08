package com.robert.banyai.sample.ui.movies

import androidx.lifecycle.SavedStateHandle
import com.robert.banyai.sample.ui.model.PMovie
import com.robert.banyai.sample.ui.mvi.MviAction
import com.robert.banyai.sample.ui.mvi.MviEffect
import com.robert.banyai.sample.ui.mvi.MviStateFactory
import com.robert.banyai.sample.ui.mvi.MviViewState
import javax.inject.Inject

interface MoviesContract {

    data class ViewState(
        val movies: List<PMovie> = emptyList()
    ) : MviViewState

    class StateFactory @Inject constructor(
        private val stateHandle: SavedStateHandle
    ) : MviStateFactory<ViewState> {

        override fun createInitialState(): ViewState {
            return ViewState()
        }
    }

    sealed class SideEffect : MviEffect {
        data class OpenDetail(
            val movieId: Int
        ) : SideEffect()
    }

    sealed class ViewAction : MviAction {
        data class OpenDetail(
            val movieId: Int
        ) : ViewAction()
    }
}