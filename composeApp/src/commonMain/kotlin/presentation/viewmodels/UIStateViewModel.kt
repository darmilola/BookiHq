package presentation.viewmodels

import UIStates.ActionUIStates
import UIStates.ScreenUIStates
import com.hoc081098.kmp.viewmodel.SavedStateHandle
import com.hoc081098.kmp.viewmodel.ViewModel
import kotlinx.coroutines.flow.StateFlow


class UIStateViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _uiState = savedStateHandle.getStateFlow("screenUiState", ScreenUIStates())
    val uiStateInfo: StateFlow<ScreenUIStates>
        get() = _uiState

    fun switchScreenUIState(screenUiStates: ScreenUIStates) {
        savedStateHandle["screenUiState"] = screenUiStates
    }
}

class ActionUIStateViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _uiState = savedStateHandle.getStateFlow("actionUiState", ActionUIStates())
    private var _postponeUiState = savedStateHandle.getStateFlow("postponeUiState", ActionUIStates())
    private var _deleteUiState = savedStateHandle.getStateFlow("deleteUiState", ActionUIStates())
    private var _availabilityUiState = savedStateHandle.getStateFlow("availabilityUiState", ActionUIStates())
    private var _joinMeetingUiState = savedStateHandle.getStateFlow("joinMeetingUiState", ActionUIStates())
    val postponeUIStateInfo: StateFlow<ActionUIStates>
        get() = _postponeUiState

    val deleteUIStateInfo: StateFlow<ActionUIStates>
        get() = _deleteUiState

    val availabilityStateInfo: StateFlow<ActionUIStates>
        get() = _availabilityUiState

    val uiStateInfo: StateFlow<ActionUIStates>
        get() = _uiState

    val joinMeetingStateInfo: StateFlow<ActionUIStates>
        get() = _joinMeetingUiState

    fun switchActionPostponeUIState(actionUIStates: ActionUIStates) {
        savedStateHandle["postponeUiState"] = actionUIStates
    }
    fun switchActionDeleteUIState(actionUIStates: ActionUIStates) {
        savedStateHandle["actionUiState"] = actionUIStates
    }

    fun switchActionAvailabilityUIState(actionUIStates: ActionUIStates) {
        savedStateHandle["availabilityUiState"] = actionUIStates
    }

    fun switchActionMeetingUIState(actionUIStates: ActionUIStates) {
        savedStateHandle["joinMeetingUiState"] = actionUIStates
    }
    fun switchActionUIState(actionUIStates: ActionUIStates) {
        savedStateHandle["actionUiState"] = actionUIStates
    }


}