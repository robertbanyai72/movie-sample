package com.robert.banyai.sample.ui.moviedetail

import androidx.lifecycle.SavedStateHandle
import com.robert.banyai.sample.ui.model.PMovie
import com.robert.banyai.sample.ui.mvi.MviAction
import com.robert.banyai.sample.ui.mvi.MviEffect
import com.robert.banyai.sample.ui.mvi.MviStateFactory
import com.robert.banyai.sample.ui.mvi.MviViewState
import javax.inject.Inject

interface MovieDetailContract {

    data class ViewState(
        val movieId: Int,
        val movie: PMovie? = null
    ) : MviViewState

    class StateFactory @Inject constructor(
        private val stateHandle: SavedStateHandle
    ) : MviStateFactory<ViewState> {

        override fun createInitialState(): ViewState {
            return ViewState(
                movieId = stateHandle.get<Int>("movieId") ?: -1
            )
        }
    }

    sealed class SideEffect : MviEffect {

    }

    sealed class ViewAction : MviAction {

    }
}