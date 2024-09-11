package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class PackageImages(@SerialName("id") val id: Long = -1, @SerialName("package_id") val packageId: Long = -1,
                           @SerialName("vendor_id") val vendorId: Long = -1, @SerialName("imageUrl") val imageUrl: String = "", var isSelected: Boolean = false): Parcelable