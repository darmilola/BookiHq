package domain.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = true) val roomId: Int = 0, @ColumnInfo @SerialName("id") val userId: Long? = null, @ColumnInfo @SerialName("email") val email: String? = "", @ColumnInfo @SerialName("firstname") val firstname: String? = null,
    @ColumnInfo @SerialName("lastname") val lastname: String? = null, @ColumnInfo @SerialName("address") val address: String? = null, @ColumnInfo @SerialName("contactPhone") val contactPhone: String? = null,
    @ColumnInfo @SerialName("country") val country: String? = null, @ColumnInfo @SerialName("city") val city: String? = null, @ColumnInfo @SerialName("gender") val gender: String = "Male",
    @ColumnInfo @SerialName("imageUrl") val profileImageUrl: String? = null,
    @ColumnInfo @SerialName("authPhone") val authPhone: String? = null, @ColumnInfo @SerialName("connectedVendor") val connectedVendorId: Long? = null,
    @ColumnInfo @SerialName("fcmToken") val fcmToken: String? = null, @ColumnInfo @SerialName("apiKey") val apiKey: String? = null,
    @ColumnInfo @SerialName("isTherapist") val isTherapist: Boolean? = false): Parcelable {
    @Ignore @SerialName("vendor_info") val vendorInfo: Vendor? = null
    }

