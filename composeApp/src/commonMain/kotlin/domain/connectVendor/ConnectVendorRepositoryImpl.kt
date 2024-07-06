package domain.connectVendor

import com.badoo.reaktive.single.Single
import domain.Models.ListDataResponse
import domain.Models.ServerResponse
import domain.Models.Vendor
import domain.Models.VendorListDataResponse
import io.ktor.client.HttpClient

 class ConnectVendorRepositoryImpl(apiService: HttpClient): ConnectVendorRepository {

     private val connectVendorNetworkService: ConnectVendorNetworkService = ConnectVendorNetworkService(apiService)
    override suspend fun connectVendor(userEmail: String, vendorId: Int): Single<ServerResponse> {
        val param = ConnectVendorRequest(userEmail, vendorId)
        return connectVendorNetworkService.connectVendor(param)
    }

    override suspend fun getVendor(country: String, nextPage: Int): Single<VendorListDataResponse> {
        val param = GetVendorRequest(country)
        return connectVendorNetworkService.getVendor(param,nextPage)
    }

    override suspend fun searchVendor(country: String, searchQuery: String, nextPage: Int): Single<VendorListDataResponse> {
        val param = SearchVendorRequest(country = country, query = searchQuery)
        return connectVendorNetworkService.searchVendor(param,nextPage)
    }
}