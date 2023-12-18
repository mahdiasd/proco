package com.proco.base

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@Stable
@Immutable
abstract class BaseViewModel<State : UiState, Event : UiEvent> : ViewModel() {
    private val initialState: State by lazy { createInitialState() }

    @Stable
    abstract fun createInitialState(): State

    @Stable
    val currentState: State get() = uiState.value

    @Stable
    abstract fun onTriggerEvent(event: Event)

    @Stable
    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)

    @Stable
    val uiState: StateFlow<State> = _uiState

    @Stable
    private val _uiEvent: MutableSharedFlow<Event> = MutableSharedFlow()

    @Stable
    val uiEvent = _uiEvent.asSharedFlow()

    fun setState(reduce: State.() -> State) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }

    protected fun setEvent(event: Event) {
        viewModelScope.launch { _uiEvent.emit(event) }
    }

    override fun onCleared() {
        _uiState.value = createInitialState()
        super.onCleared()
    }

}