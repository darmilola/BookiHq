package presentation.Packages

import UIStates.AppUIStates
import domain.Models.HomepageInfo
import domain.Models.VendorPackage
import domain.Models.VendorStatusModel


class PackageContract {
    interface View {
        fun showLoadPackageLce(appUIStates: AppUIStates)
        fun showVendorPackages(vendorPackages: List<VendorPackage>)
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun getVendorPackages(vendorId: Long)
    }
}