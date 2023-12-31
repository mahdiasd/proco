package app.ir.main

import androidx.lifecycle.viewModelScope
import com.proco.base.BaseViewModel
import com.proco.domain.fake_data.FakeData
import com.proco.domain.usecase.user.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUseCase: GetUserUseCase,
) : BaseViewModel<MainUiState, MainUiEvent, MainUiEffect>() {

    init {
        getUser()
    }


    private fun getUser() {
        viewModelScope.launch {
            getUseCase.executeSync(GetUserUseCase.DataSourceType.Local).collect {
//                setState { currentState.copy(user = it) }
                // TODO: replaced real data with this line
                setState { currentState.copy(user = FakeData.user()) }
            }
        }
    }

    override fun createInitialState() = MainUiState()

    override fun onTriggerEvent(event: MainUiEvent) {
    }

}
