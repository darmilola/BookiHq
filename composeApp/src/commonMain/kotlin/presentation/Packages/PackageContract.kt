package presentation.Packages

import UIStates.AppUIStates
import domain.Models.HomepageInfo
import domain.Models.VendorPackage
import domain.Models.VendorPackageResourceListEnvelope
import domain.Models.VendorStatusModel


class PackageContract {
    interface View {
        fun showLoadPackageLce(appUIStates: AppUIStates)
        fun showVendorPackages(vendorPackages: VendorPackageResourceListEnvelope)
        fun onLoadMoreAppointmentStarted()
        fun onLoadMoreAppointmentEnded()
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun getVendorPackages(vendorId: Long)
        abstract fun getMoreVendorPackages(vendorId: Long, nextPage: Int)
    }
}