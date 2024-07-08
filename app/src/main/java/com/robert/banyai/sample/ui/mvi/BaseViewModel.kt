package com.robert.banyai.sample.ui.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<VS : MviViewState, VA : MviAction, VE : MviEffect>(
    initialState: VS
) : ViewModel() {

    private val actions = Channel<VA>(Channel.UNLIMITED)

    protected val currentState: VS
        get() = _state.value

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<VS>
        get() = _state

    private val _effect: Channel<VE> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        handleActions()
    }

    abstract suspend fun onActionReceived(action: VA)

    private fun handleActions() {
        viewModelScope.launch {
            actions.consumeAsFlow().collect(::onActionReceived)
        }
    }

    protected fun updateState(reduce: VS.() -> VS) {
        _state.update(reduce)
    }

    fun sendAction(action: VA) {
        viewModelScope.launch {
            actions.send(action)
        }
    }

    protected fun sideEffect(effect: VE) {
        viewModelScope.launch { _effect.send(effect) }
    }

    override fun onCleared() {
        super.onCleared()
        _effect.close()
    }
}
