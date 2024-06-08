package presentation.viewmodels

import com.hoc081098.kmp.viewmodel.SavedStateHandle
import com.hoc081098.kmp.viewmodel.ViewModel
import kotlinx.coroutines.flow.StateFlow


class ScreenUIStateViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _uiState = savedStateHandle.getStateFlow("screenUiState", ScreenUIStates())
    val uiStateInfo: StateFlow<ScreenUIStates>
        get() = _uiState

    fun switchScreenUIState(screenUiStates: ScreenUIStates) {
        savedStateHandle["screenUiState"] = screenUiStates
    }
}

class ActionUIStateViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _uiState = savedStateHandle.getStateFlow("actionUiState", ActionUIStates())
    val uiStateInfo: StateFlow<ActionUIStates>
        get() = _uiState

    fun switchActionUIState(actionUIStates: ActionUIStates) {
        savedStateHandle["actionUiState"] = actionUIStates
    }
}