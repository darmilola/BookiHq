package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable @Parcelize
data class Vendor(
    @SerialName("id") val vendorId: Int? = null, @SerialName("businessEmail") val businessEmail: String = "",
    @SerialName("businessName") val businessName: String = "", @SerialName("businessLogo") val businessLogo: String = "",
    @SerialName("businessAddress") val businessAddress: String = "", @SerialName("businessHandle") val businessHandle: String = "",
    @SerialName("businessAbout") val businessAbout: String = "", @SerialName("openingTime") val openingTime: String = "",
    @SerialName("country") val country: String? = null, @SerialName("city") val city: String? = null,
    @SerialName("isMobileServicesAvailable") val isMobileServiceAvailable: Boolean = false, val isSelected: Boolean = false): Parcelable

data class VendorItemUIModel(
    val selectedVendor: Vendor,
    val vendorsList: List<Vendor>)

fun getVendorListItemViewHeight(
    itemList: List<Vendor>
): Int {
    val itemCount = itemList.size

    return itemCount * 250
}

