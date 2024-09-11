package domain.packages

import com.badoo.reaktive.single.Single
import domain.Models.OrderListDataResponse
import domain.Models.ServerResponse
import domain.Models.VendorPackageListDataResponse
import domain.Orders.AddProductReviewRequest
import domain.Orders.GetOrderRequest
import domain.Orders.OrderNetworkService
import domain.Orders.OrderRepository
import io.ktor.client.HttpClient

class PackageRepositoryImpl(apiService: HttpClient): PackageRepository {
    private val packageNetworkService: PackageNetworkService = PackageNetworkService(apiService)

    override suspend fun getVendorPackages(vendorId: Long): Single<VendorPackageListDataResponse> {
        val param = GetVendorPackageRequest(vendorId)
        return packageNetworkService.getVendorPackages(param)
    }


}