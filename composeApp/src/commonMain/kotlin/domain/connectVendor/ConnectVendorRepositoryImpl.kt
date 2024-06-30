package domain.connectVendor

import com.badoo.reaktive.single.Single
import domain.Models.ListDataResponse
import domain.Models.ServerResponse
import domain.Models.Vendor
import domain.authentication.AuthenticationNetworkService
import io.ktor.client.HttpClient

 class ConnectVendorRepositoryImpl(apiService: HttpClient): ConnectVendorRepository {

     private val connectVendorNetworkService: ConnectVendorNetworkService = ConnectVendorNetworkService(apiService)
    override suspend fun connectVendor(userEmail: String, vendorId: Int): Single<ServerResponse> {
        val param = ConnectVendorRequest(userEmail, vendorId)
        return connectVendorNetworkService.connectVendor(param)
    }

    override suspend fun getVendor(countryId: Int, cityId: Int, nextPage: Int): Single<ListDataResponse<Vendor>> {
        val param = GetVendorRequest(cityId, countryId)
        return connectVendorNetworkService.getVendor(param,nextPage)
    }

    override suspend fun searchVendor(
        countryId: Int,
        cityId: Int,
        searchQuery: String,
        nextPage: Int
    ): Single<ListDataResponse<Vendor>> {
        val param = SearchVendorRequest(countryId = countryId, cityId = cityId, query = searchQuery)
        return connectVendorNetworkService.searchVendor(param,nextPage)
    }
}