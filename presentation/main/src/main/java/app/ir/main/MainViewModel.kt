package app.ir.main

import androidx.lifecycle.viewModelScope
import com.proco.base.BaseViewModel
import com.proco.domain.model.network.DataResult
import com.proco.domain.usecase.user.GetLocalUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getLocalUserUseCase: GetLocalUserUseCase,
) : BaseViewModel<MainUiState, MainUiEvent>() {

    init {
        getUser()
    }


    private fun getUser() {
        viewModelScope.launch {
            getLocalUserUseCase.executeSync(Unit).collect {
                when (it) {
                    is DataResult.Failure -> {
                        setState { currentState.copy(userNotFound = true) }
                    }

                    is DataResult.Success -> {
                        setState { currentState.copy(user = it.data) }
                    }
                }
            }
        }
    }

    override fun createInitialState() = MainUiState()

    override fun onTriggerEvent(event: MainUiEvent) {
    }

}
