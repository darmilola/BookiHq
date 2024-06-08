package presentation.viewmodels

import com.hoc081098.kmp.viewmodel.SavedStateHandle
import com.hoc081098.kmp.viewmodel.ViewModel
import domain.Models.HomepageInfo
import domain.Models.VendorStatusModel
import kotlinx.coroutines.flow.StateFlow

class HomePageViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

        private var _homePageInfo =  savedStateHandle.getStateFlow("homePageInfo",HomepageInfo())
        private var _vendorStatus =  savedStateHandle.getStateFlow("vendorStatus", arrayListOf<VendorStatusModel>())
        private var _homePageViewHeight =  savedStateHandle.getStateFlow("homePageViewHeight",0)

        val homePageInfo: StateFlow<HomepageInfo>
            get() = _homePageInfo

       val vendorStatus: StateFlow<ArrayList<VendorStatusModel>>
        get() = _vendorStatus

      val homePageViewHeight: StateFlow<Int>
        get() = _homePageViewHeight

        fun setHomePageInfo(homepageInfo: HomepageInfo) {
            savedStateHandle["homePageInfo"] = homepageInfo
        }

      fun setVendorStatus(vendorStatus: ArrayList<VendorStatusModel>) {
        savedStateHandle["vendorStatus"] = vendorStatus
     }

      fun setHomePageViewHeight(viewHeight: Int) {
        savedStateHandle["homePageViewHeight"] = viewHeight
     }
}