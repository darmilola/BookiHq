package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable @Parcelize
data class Vendor(
    @SerialName("id") val vendorId: Long? = null, @SerialName("businessEmail") val businessEmail: String? = null,
    @SerialName("businessName") val businessName: String? = null, @SerialName("businessLogo") val businessLogo: String? = null,
    @SerialName("businessAddress") val businessAddress: String? = null, @SerialName("businessHandle") val businessHandle: String? = null,
    @SerialName("businessAbout") val businessAbout: String? = null, @SerialName("openingTime") val openingTime: String? = null,
    @SerialName("latitude") val latitude: Double = 0.0, @SerialName("longitude") val longitude: Double = 0.0, @SerialName("deliveryFee") val deliveryFee: Long = 0L,
    @SerialName("fcmToken") val fcmToken: String? = null, @SerialName("country") val country: String? = null, @SerialName("city") val city: String? = null, @SerialName("whatsappPhone") val whatsAppPhone: String? = null,
    @SerialName("isMobileServicesAvailable") val isMobileServiceAvailable: Boolean = false, val isSelected: Boolean = false, var distanceFromCustomer: Double? = 0.0): Parcelable

data class VendorItemUIModel(
    val selectedVendor: Vendor = Vendor(),
    val vendorsList: List<Vendor> = listOf()
)

fun getVendorListItemViewHeight(
    itemList: List<Vendor>
): Int {
    val itemCount = itemList.size

    return itemCount * 250
}

