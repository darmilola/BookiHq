package domain.packages

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetVendorPackageRequest(@SerialName("vendor_id") val vendorId: Long)