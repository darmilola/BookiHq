package presentation.connectVendor

import domain.Models.ResourceListEnvelope
import domain.Models.Vendor
import UIStates.ScreenUIStates
import domain.Models.PlatformNavigator
import domain.Models.VendorResourceListEnvelope

class ConnectVendorContract {
    interface View {
        fun showLce(uiState: ScreenUIStates)
        fun onVendorConnected(userId: Long)
        fun showVendors(vendors: VendorResourceListEnvelope?, isFromSearch: Boolean = false)
        fun onLoadMoreVendorStarted(isSuccess: Boolean = false)
        fun onLoadMoreVendorEnded(isSuccess: Boolean = false)
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun connectVendor(userId: Long, vendorId: Long, action: String, userFirstname: String, vendor: Vendor, platformNavigator: PlatformNavigator)
        abstract fun getVendor(country: String)
        abstract fun getMoreVendor(country: String,nextPage: Int = 1)
        abstract fun searchVendor(country: String, searchQuery: String)
        abstract fun searchMoreVendors(country: String,searchQuery: String, nextPage: Int = 1)
    }
}