package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import domain.Enums.RecommendationType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable @Parcelize
data class VendorRecommendation(@SerialName("id") val recommendationId: Long = -1, @SerialName("vendor_id") val vendorId: Long = -1,
                                @SerialName("recommendationType") val recommendationType: String = RecommendationType.Services.toPath(),
                                @SerialName("description") val description: String = "", @SerialName("product_id") val productId: Long = -1,
                                @SerialName("service_type_id") val serviceTypeId: Long = -1, @SerialName("service_type") val serviceTypeItem: ServiceTypeItem? = null,
                                @SerialName("product") val product: Product? = null, @SerialName("imageUrl") val imageUrl: String = "", val isSelected: Boolean = false): Parcelable