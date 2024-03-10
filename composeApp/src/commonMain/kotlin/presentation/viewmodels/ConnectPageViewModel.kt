package presentation.viewmodels

import com.hoc081098.kmp.viewmodel.SavedStateHandle
import com.hoc081098.kmp.viewmodel.ViewModel
import domain.Models.Vendor
import kotlinx.coroutines.flow.StateFlow

class ConnectPageViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _selectedVendor =  savedStateHandle.getStateFlow("selectedVendor", Vendor())

    val selectedVendor: StateFlow<Vendor>
        get() = _selectedVendor

    fun selectVendorOrder(vendor: Vendor) {
        savedStateHandle["selectedVendor"] = vendor
    }

}