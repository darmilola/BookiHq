package presentation.DomainViewHandler

import UIStates.AppUIStates
import dev.jordond.compass.Place
import domain.Models.Vendor
import domain.Models.VendorPackage
import domain.Models.VendorPackageResourceListEnvelope
import presentation.Packages.PackageContract
import presentation.Packages.PackagePresenter
import presentation.profile.ProfileContract
import presentation.profile.ProfilePresenter
import presentation.viewmodels.AppointmentResourceListEnvelopeViewModel
import presentation.viewmodels.LoadingScreenUIStateViewModel
import presentation.viewmodels.PackagesResourceListEnvelopeViewModel
import presentation.viewmodels.PerformedActionUIStateViewModel

class PackagesHandler(
    private val packagePresenter: PackagePresenter,
    private val packagesResourceListEnvelopeViewModel: PackagesResourceListEnvelopeViewModel,
    private val loadingScreenUiStateViewModel: LoadingScreenUIStateViewModel
) : PackageContract.View {
    fun init() {
        packagePresenter.registerUIContract(this)
    }

    override fun showLoadPackageLce(appUIStates: AppUIStates) {
        loadingScreenUiStateViewModel.switchScreenUIState(appUIStates)
    }

    override fun showVendorPackages(vendorPackages: VendorPackageResourceListEnvelope) {
        if (packagesResourceListEnvelopeViewModel.resources.value.isEmpty()){
            packagesResourceListEnvelopeViewModel.setResources(vendorPackages.data)
            vendorPackages.prevPageUrl?.let { packagesResourceListEnvelopeViewModel.setPrevPageUrl(it) }
            vendorPackages.nextPageUrl?.let { packagesResourceListEnvelopeViewModel.setNextPageUrl(it) }
            vendorPackages.currentPage?.let { packagesResourceListEnvelopeViewModel.setCurrentPage(it) }
            vendorPackages.totalItemCount?.let { packagesResourceListEnvelopeViewModel.setTotalItemCount(it) }
            vendorPackages.displayedItemCount?.let { packagesResourceListEnvelopeViewModel.setDisplayedItemCount(it) }
        }
        else {
            val appointmentList = packagesResourceListEnvelopeViewModel.resources.value
            appointmentList.addAll(vendorPackages.data!!)
            packagesResourceListEnvelopeViewModel.setResources(appointmentList)
            vendorPackages.prevPageUrl?.let { packagesResourceListEnvelopeViewModel.setPrevPageUrl(it) }
            vendorPackages.nextPageUrl?.let { packagesResourceListEnvelopeViewModel.setNextPageUrl(it) }
            vendorPackages.currentPage?.let { packagesResourceListEnvelopeViewModel.setCurrentPage(it) }
            vendorPackages.totalItemCount?.let { packagesResourceListEnvelopeViewModel.setTotalItemCount(it) }
            vendorPackages.displayedItemCount?.let { packagesResourceListEnvelopeViewModel.setDisplayedItemCount(it) }
        }
    }

    override fun onLoadMorePackageStarted() {
        packagesResourceListEnvelopeViewModel.setLoadingMore(true)
    }

    override fun onLoadMorePackageEnded() {
        packagesResourceListEnvelopeViewModel.setLoadingMore(false)
    }


}
