package domain.packages

import com.badoo.reaktive.single.Single
import domain.Models.OrderListDataResponse
import domain.Models.ServerResponse
import domain.Models.VendorPackageListDataResponse

interface PackageRepository {
    suspend fun getVendorPackages(vendorId: Long): Single<VendorPackageListDataResponse>
}