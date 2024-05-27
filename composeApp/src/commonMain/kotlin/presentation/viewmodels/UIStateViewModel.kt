package presentation.viewmodels

import com.hoc081098.kmp.viewmodel.SavedStateHandle
import com.hoc081098.kmp.viewmodel.ViewModel
import com.hoc081098.kmp.viewmodel.wrapper.wrap
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow



class UIStateViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _uiState = savedStateHandle.getStateFlow("uiState", UIStates()).wrap()
    val uiData: StateFlow<UIStates>
        get() = _uiState

    fun switchState(uiStates: UIStates) {
        savedStateHandle["uiState"] = uiStates
    }
}

class AsyncUIStateViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _uiState = savedStateHandle.getStateFlow("uiState", UIStates()).wrap()
    val uiData: StateFlow<UIStates>
        get() = _uiState

    fun switchState(uiStates: UIStates) {
        savedStateHandle["uiState"] = uiStates
    }
}