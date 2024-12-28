package domain.connectVendor

import com.badoo.reaktive.single.Single
import domain.Models.ListDataResponse
import domain.Models.ServerResponse
import domain.Models.Vendor
import domain.Models.VendorListDataResponse
import domain.Models.ViewVendorsResponse
import kotlinx.serialization.SerialName

interface ConnectVendorRepository {
    suspend fun connectVendor(userId: Long, vendorId: Long, action: String): Single<ServerResponse>
    suspend fun getVendor(country: String, state: Long, connectedVendor: Long, nextPage: Int): Single<VendorListDataResponse>
    suspend fun searchVendor(country: String, connectedVendor: Long, searchQuery: String, nextPage: Int = 1): Single<VendorListDataResponse>
    suspend fun viewVendors(country: String, state: Long, connectedVendor: Long): Single<ViewVendorsResponse>
}