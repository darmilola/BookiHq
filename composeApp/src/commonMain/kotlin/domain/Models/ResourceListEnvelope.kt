package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import domain.Enums.BookingStatus
import domain.Enums.PaymentMethod
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ResourceListEnvelope<T : Any>(
    @SerialName("data") val resources: MutableList<@UnsafeVariance T>? = null,
    @SerialName("next_page_url") val nextPageUrl: String? = null,
    @SerialName("prev_page_url") val prevPageUrl: String? = null,
    @SerialName("per_page") val perPage: String? = null,
    @SerialName("current_page") val currentPage: Int? = null,
    @SerialName("to") var displayedItemCount: Int? = null,
    @SerialName("total") var totalItemCount: Int? = null,
    @SerialName("path") var path: String? = null)


@Serializable @Parcelize
class AppointmentResourceListEnvelope(
    @SerialName("data") val data: MutableList<UserAppointment>? = null,
    @SerialName("next_page_url") val nextPageUrl: String? = null,
    @SerialName("prev_page_url") val prevPageUrl: String? = null,
    @SerialName("per_page") val perPage: String? = null,
    @SerialName("current_page") val currentPage: Int? = null,
    @SerialName("to") var displayedItemCount: Int? = null,
    @SerialName("total") var totalItemCount: Int? = null,
    @SerialName("path") var path: String? = null): Parcelable

@Serializable @Parcelize
class TherapistAppointmentResourceListEnvelope(
    @SerialName("data") val data: MutableList<Appointment>? = null,
    @SerialName("next_page_url") val nextPageUrl: String? = null,
    @SerialName("prev_page_url") val prevPageUrl: String? = null,
    @SerialName("per_page") val perPage: String? = null,
    @SerialName("current_page") val currentPage: Int? = null,
    @SerialName("to") var displayedItemCount: Int? = null,
    @SerialName("total") var totalItemCount: Int? = null,
    @SerialName("path") var path: String? = null): Parcelable

@Serializable @Parcelize
class UserAppointment(
    @SerialName("id") val id: Long = -1,
    @SerialName("user_id") val userId: Long = -1,
    @SerialName("appointment_id") val appointmentId: Long = -1,
    @SerialName("paymentMethod") val paymentMethod: String? = PaymentMethod.CARD_PAYMENT.toPath(),
    @SerialName("bookingStatus") val bookingStatus: String? = BookingStatus.PENDING.toPath(),
    @SerialName("appointments") val resources: Appointment? = null): Parcelable


@Serializable @Parcelize
class ProductResourceListEnvelope(
    @SerialName("data") val resources: MutableList<Product>? = null,
    @SerialName("next_page_url") val nextPageUrl: String? = null,
    @SerialName("prev_page_url") val prevPageUrl: String? = null,
    @SerialName("per_page") val perPage: String? = null,
    @SerialName("current_page") val currentPage: Int? = null,
    @SerialName("to") var displayedItemCount: Int? = null,
    @SerialName("total") var totalItemCount: Int? = null,
    @SerialName("path") var path: String? = null): Parcelable


@Serializable @Parcelize
class OrderResourceListEnvelope(
    @SerialName("data") val resources: MutableList<UserOrders>? = null,
    @SerialName("next_page_url") val nextPageUrl: String? = null,
    @SerialName("prev_page_url") val prevPageUrl: String? = null,
    @SerialName("per_page") val perPage: String? = null,
    @SerialName("current_page") val currentPage: Int? = null,
    @SerialName("to") var displayedItemCount: Int? = null,
    @SerialName("total") var totalItemCount: Int? = null,
    @SerialName("path") var path: String? = null): Parcelable


@Serializable @Parcelize
class VendorResourceListEnvelope(
    @SerialName("data") var resources: List<Vendor>? = null,
    @SerialName("next_page_url") val nextPageUrl: String? = null,
    @SerialName("prev_page_url") val prevPageUrl: String? = null,
    @SerialName("per_page") val perPage: String? = null,
    @SerialName("current_page") val currentPage: Int? = null,
    @SerialName("to") var displayedItemCount: Int? = null,
    @SerialName("total") var totalItemCount: Int? = null,
    @SerialName("path") var path: String? = null): Parcelable
