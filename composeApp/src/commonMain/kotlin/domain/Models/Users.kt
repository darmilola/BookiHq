package domain.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import domain.Enums.Gender
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = true) var roomId: Int = 0, @ColumnInfo @SerialName("id") var userId: Long? = null, @ColumnInfo @SerialName("email") var email: String? = "", @ColumnInfo @SerialName("firstname") var firstname: String? = null,
    @ColumnInfo @SerialName("lastname") var lastname: String? = null, @ColumnInfo @SerialName("address") var address: String = "", @ColumnInfo @SerialName("contactPhone") var contactPhone: String = "",
    @ColumnInfo @SerialName("country") var country: String = "", @ColumnInfo @SerialName("gender") var gender: String = Gender.MALE.toPath(),
    @ColumnInfo @SerialName("imageUrl") var profileImageUrl: String? = null,
    @ColumnInfo @SerialName("authPhone") var authPhone: String? = null, @ColumnInfo @SerialName("connectedVendor") var connectedVendorId: Long? = null,
    @ColumnInfo @SerialName("fcmToken") var fcmToken: String? = null, @ColumnInfo @SerialName("apiKey") var apiKey: String? = null,
    @ColumnInfo @SerialName("isTherapist") var isTherapist: Boolean? = false): Parcelable {
    @Ignore @SerialName("vendor_info") var vendorInfo: Vendor? = null
    @Ignore @SerialName("state") var state: State? = null
    }

