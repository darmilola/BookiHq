package domain.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Vendor(
    @SerialName("id")
    val vendorId: Int? = null,
    @SerialName("businessEmail")
    val businessEmail: String = "",
    @SerialName("businessName")
    val businessName: String = "",
    @SerialName("businessLogo")
    val businessLogo: String = "",
    @SerialName("businessAddress")
    val businessAddress: String = "",
    @SerialName("businessHandle")
    val businessHandle: String = "",
    @SerialName("businessAbout")
    val businessAbout: String = "",
    @SerialName("openingHour")
    val openingHour: Int = 0,
    @SerialName("closingHour")
    val closingHour: Int = 0,
    @SerialName("openingMinute")
    val openingMinute: Int = 0,
    @SerialName("closingMinute")
    val closingMinute: Int = 0,
    @SerialName("countryId")
    val countryId: Int = -1,
    @SerialName("cityId")
    val cityId: Int = -1,
    @SerialName("homeServiceAvailable")
    val homeServiceAvailable: Boolean = false,
    @SerialName("contactPhone")
    val contactPhone: String = "",
    @SerialName("password")
    val password: String = "",
    var isSelected: Boolean = false
)

data class VendorItemUIModel(
    val selectedVendor: Vendor,
    val vendorsList: List<Vendor>)

fun getVendorListItemViewHeight(
    itemList: List<Vendor>
): Int {
    val itemCount = itemList.size

    return itemCount * 250
}

