package com.proco.base

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@Stable
@Immutable
abstract class BaseViewModel<State : UiState, Event : UiEvent, Effect : UiEffect> : ViewModel() {
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

    private val _effect: Channel<Effect> = Channel()
    val uiEffect = _effect.receiveAsFlow()


    fun setState(reduce: State.() -> State) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }

    protected fun setEvent(event: Event) {
        viewModelScope.launch { _uiEvent.emit(event) }
    }

    protected fun   setEffect(builder: () -> Effect) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }

    override fun onCleared() {
        _uiState.value = createInitialState()
        super.onCleared()
    }

}