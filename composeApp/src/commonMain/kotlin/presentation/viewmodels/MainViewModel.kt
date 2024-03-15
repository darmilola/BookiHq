package presentation.viewmodels

import com.hoc081098.kmp.viewmodel.SavedStateHandle
import com.hoc081098.kmp.viewmodel.ViewModel
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import domain.Models.ServiceCategoryItem
import domain.Models.Services
import domain.Models.User
import domain.Models.Vendor
import kotlinx.coroutines.flow.StateFlow

class MainViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _screenTitle = savedStateHandle.getStateFlow("screenTitle","")
    private var _connectedVendor =  savedStateHandle.getStateFlow("connectedVendor", Vendor())
    private var _currentUserInfo =  savedStateHandle.getStateFlow("userInfo", User())
    private var _screenNav =  savedStateHandle.getStateFlow("screenNav", Pair(-1,-1))
    private var _selectedService =  savedStateHandle.getStateFlow("selectedService", Services())

    val screenTitle: StateFlow<String>
        get() = _screenTitle

    val selectedService: StateFlow<Services>
        get() = _selectedService

    val connectedVendor: StateFlow<Vendor>
        get() = _connectedVendor

    val currentUserInfo: StateFlow<User>
        get() = _currentUserInfo

    val screenNav: StateFlow<Pair<Int,Int>>
        get() = _screenNav

    fun setTitle(newTitle: String) {
        savedStateHandle["screenTitle"] = newTitle
    }

    fun setScreenNav(screenNav: Pair<Int,Int>) {
        savedStateHandle["screenNav"] = screenNav
    }

    fun setConnectedVendor(vendor: Vendor) {
        savedStateHandle["connectedVendor"] = vendor
    }

    fun setUserInfo(user: User) {
        savedStateHandle["userInfo"] = user
    }

    fun setSelectedService(selectedService: Services) {
        savedStateHandle["selectedService"] = selectedService
    }
}