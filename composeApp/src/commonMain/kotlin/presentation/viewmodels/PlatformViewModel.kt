package presentation.viewmodels

import com.hoc081098.kmp.viewmodel.SavedStateHandle
import com.hoc081098.kmp.viewmodel.ViewModel
import domain.Models.Vendor
import kotlinx.coroutines.flow.StateFlow

class PlatformViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _platformCities = savedStateHandle.getStateFlow("platformCities", arrayListOf<String>())

    val platformCities: StateFlow<ArrayList<String>>
        get() = _platformCities

    fun setPlatformCities(cities: ArrayList<String>) {
        savedStateHandle["platformCities"] = cities
    }

}