package domain.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity
@Parcelize
data class Vendor(
    @PrimaryKey(autoGenerate = true) val roomId: Int = 0, @ColumnInfo @SerialName("id") val vendorId: Long? = null, @ColumnInfo @SerialName("businessEmail") val businessEmail: String? = null,
    @ColumnInfo @SerialName("businessName") val businessName: String? = null, @ColumnInfo @SerialName("businessLogo") val businessLogo: String? = null,
    @ColumnInfo @SerialName("businessAddress") val businessAddress: String? = null, @ColumnInfo @SerialName("businessHandle") val businessHandle: String? = null,
    @ColumnInfo @SerialName("businessAbout") val businessAbout: String? = null, @ColumnInfo @SerialName("openingTime") val openingTime: String? = null,
    @ColumnInfo @SerialName("latitude") val latitude: Double = 0.0, @ColumnInfo @SerialName("longitude") val longitude: Double = 0.0, @ColumnInfo @SerialName("deliveryFee") val deliveryFee: Long = 0L,
    @ColumnInfo @SerialName("fcmToken") val fcmToken: String? = null, @ColumnInfo @SerialName("country") val country: String? = null, @ColumnInfo @SerialName("city") val city: String? = null, @ColumnInfo @SerialName("whatsappPhone") val whatsAppPhone: String? = null,
    @ColumnInfo @SerialName("isMobileServicesAvailable") val isMobileServiceAvailable: Boolean = false, val isSelected: Boolean = false, var distanceFromCustomer: Double? = 0.0, var minuteDriveText: String = ""): Parcelable

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

