package presentation.UserProfile.ConnectVendor

import com.badoo.reaktive.single.Single
import domain.Models.ListDataResponse
import domain.Models.ResourceListEnvelope
import domain.Models.ServerResponse
import domain.Models.User
import domain.Models.Vendor
import presentation.viewmodels.UIStates

class ConnectVendorContract {
    interface View {
        fun showLce(uiState: UIStates)
        fun onVendorConnected(userEmail: String)
        fun showVendors(vendors: ResourceListEnvelope<Vendor>?, isFromSearch: Boolean = false)
        fun onLoadMoreVendorStarted(isSuccess: Boolean = false)
        fun onLoadMoreVendorEnded(isSuccess: Boolean = false)
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun connectVendor(userEmail: String, vendorId: Int)
        abstract fun getVendor(countryId: Int, cityId: Int)
        abstract fun getMoreVendor(countryId: Int, cityId: Int, nextPage: Int = 1)
        abstract fun searchVendor(countryId: Int, cityId: Int, searchQuery: String)
        abstract fun searchMoreVendors(countryId: Int, cityId: Int, searchQuery: String, nextPage: Int = 1)
    }
}