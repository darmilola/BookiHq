package domain.connectVendor

import com.badoo.reaktive.single.Single
import domain.Models.ListDataResponse
import domain.Models.ServerResponse
import domain.Models.Vendor
import domain.Models.VendorListDataResponse

interface ConnectVendorRepository {
    suspend fun connectVendor(userId: Long, vendorId: Long, action: String): Single<ServerResponse>
    suspend fun getVendor(country: String, city: String, nextPage: Int = 1): Single<VendorListDataResponse>
    suspend fun searchVendor(country: String, city: String, searchQuery: String, nextPage: Int = 1): Single<VendorListDataResponse>
}