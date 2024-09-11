package presentation.DomainViewHandler

import UIStates.AppUIStates
import dev.jordond.compass.Place
import domain.Models.Vendor
import domain.Models.VendorPackage
import presentation.Packages.PackageContract
import presentation.Packages.PackagePresenter
import presentation.profile.ProfileContract
import presentation.profile.ProfilePresenter
import presentation.viewmodels.LoadingScreenUIStateViewModel
import presentation.viewmodels.PerformedActionUIStateViewModel

class PackagesHandler(
    private val packagePresenter: PackagePresenter,
    private val loadingScreenUiStateViewModel: LoadingScreenUIStateViewModel,
    private val onVendorPackageReady: (List<VendorPackage>) -> Unit
) : PackageContract.View {
    fun init() {
        packagePresenter.registerUIContract(this)
    }

    override fun showLoadPackageLce(appUIStates: AppUIStates) {
        loadingScreenUiStateViewModel.switchScreenUIState(appUIStates)
    }

    override fun showVendorPackages(vendorPackages: List<VendorPackage>) {
        onVendorPackageReady(vendorPackages)
    }


}
