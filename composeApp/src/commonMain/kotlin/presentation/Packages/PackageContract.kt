package presentation.Packages

import UIStates.AppUIStates
import domain.Models.VendorPackageResourceListEnvelope


class PackageContract {
    interface View {
        fun showLoadPackageLce(appUIStates: AppUIStates)
        fun showVendorPackages(vendorPackages: VendorPackageResourceListEnvelope)
        fun onLoadMorePackageStarted()
        fun onLoadMorePackageEnded()
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun getVendorPackages(vendorId: Long)
        abstract fun getMoreVendorPackages(vendorId: Long, nextPage: Int)
    }
}