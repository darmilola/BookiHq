package infrastructure.connectVendor

import com.badoo.reaktive.single.Single
import domain.Models.ListDataResponse
import domain.Models.ServerResponse
import domain.Models.Vendor

interface ConnectVendorRepository {
    suspend fun connectVendor(userEmail: String, vendorId: Int): Single<ServerResponse>
    suspend fun getVendor(countryId: Int, cityId: Int, nextPage: Int = 1): Single<ListDataResponse<Vendor>>
    suspend fun searchVendor(countryId: Int, cityId: Int, searchQuery: String, nextPage: Int = 1): Single<ListDataResponse<Vendor>>
}