package presentation.viewmodels

import com.hoc081098.kmp.viewmodel.SavedStateHandle
import com.hoc081098.kmp.viewmodel.ViewModel
import domain.Models.State
import kotlinx.coroutines.flow.StateFlow

class StatesViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _platformStates = savedStateHandle.getStateFlow("states", arrayListOf<State>())

    val platformStates: StateFlow<ArrayList<State>>
        get() = _platformStates

    fun setStates(cities: ArrayList<State>) {
        savedStateHandle["states"] = cities
    }

}