package com.robert.banyai.sample.ui.mvi

interface MviAction

interface MviEffect

interface MviViewState

interface MviStateFactory<State : MviViewState> {
    fun createInitialState(): State
}
