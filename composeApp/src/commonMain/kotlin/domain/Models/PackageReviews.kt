package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class PackageReviews(@SerialName("id") val id: Long = -1, @SerialName("package_id") val packageId: Long = -1,
                          @SerialName("reviewText") val reviewText: String, @SerialName("customer_info") val customerInfo: User,
                          @SerialName("created_at") val createdAt: String = "",): Parcelable