package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class AppointmentReview(
    @SerialName("id") val reviewId: Long? = -1, @SerialName("appointment_id") val appointmentId: Long? = -1, @SerialName("vendor_id") val vendorId: Long = -1,
    @SerialName("user_id") val userId: Long = -1, @SerialName("service_type_id") val serviceTypeId: Long = -1,
    @SerialName("reviewText") val reviewText: String = "", @SerialName("created_at") val createdAt: String = "",
    @SerialName("customer_info") val customerInfo: User = User()): Parcelable