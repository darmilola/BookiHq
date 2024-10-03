package presentation.viewmodels

import com.hoc081098.kmp.viewmodel.SavedStateHandle
import com.hoc081098.kmp.viewmodel.ViewModel
import kotlinx.coroutines.flow.StateFlow

class CityViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _platformCities = savedStateHandle.getStateFlow("cities", arrayListOf<String>())

    val cities: StateFlow<ArrayList<String>>
        get() = _platformCities

    fun setCities(cities: ArrayList<String>) {
        savedStateHandle["cities"] = cities
    }

}