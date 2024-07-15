package domain.Models

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


@Serializable
class AppointmentResourceListEnvelope(
    @SerialName("data") val data: MutableList<UserAppointmentsData>? = null,
    @SerialName("next_page_url") val nextPageUrl: String? = null,
    @SerialName("prev_page_url") val prevPageUrl: String? = null,
    @SerialName("per_page") val perPage: String? = null,
    @SerialName("current_page") val currentPage: Int? = null,
    @SerialName("to") var displayedItemCount: Int? = null,
    @SerialName("total") var totalItemCount: Int? = null,
    @SerialName("path") var path: String? = null)

@Serializable
class TherapistAppointmentResourceListEnvelope(
    @SerialName("data") val data: MutableList<Appointment>? = null,
    @SerialName("next_page_url") val nextPageUrl: String? = null,
    @SerialName("prev_page_url") val prevPageUrl: String? = null,
    @SerialName("per_page") val perPage: String? = null,
    @SerialName("current_page") val currentPage: Int? = null,
    @SerialName("to") var displayedItemCount: Int? = null,
    @SerialName("total") var totalItemCount: Int? = null,
    @SerialName("path") var path: String? = null)

@Serializable
class UserAppointmentsData(
    @SerialName("id") val id: Int = -1,
    @SerialName("user_id") val userId: Int = -1,
    @SerialName("appointment_id") val appointmentId: Int = -1,
    @SerialName("created_at") val created_at: String? = "",
    @SerialName("appointments") val resources: Appointment? = null)


@Serializable
class ProductResourceListEnvelope(
    @SerialName("data") val resources: MutableList<Product>? = null,
    @SerialName("next_page_url") val nextPageUrl: String? = null,
    @SerialName("prev_page_url") val prevPageUrl: String? = null,
    @SerialName("per_page") val perPage: String? = null,
    @SerialName("current_page") val currentPage: Int? = null,
    @SerialName("to") var displayedItemCount: Int? = null,
    @SerialName("total") var totalItemCount: Int? = null,
    @SerialName("path") var path: String? = null)


@Serializable
class OrderResourceListEnvelope(
    @SerialName("data") val resources: MutableList<UserOrders>? = null,
    @SerialName("next_page_url") val nextPageUrl: String? = null,
    @SerialName("prev_page_url") val prevPageUrl: String? = null,
    @SerialName("per_page") val perPage: String? = null,
    @SerialName("current_page") val currentPage: Int? = null,
    @SerialName("to") var displayedItemCount: Int? = null,
    @SerialName("total") var totalItemCount: Int? = null,
    @SerialName("path") var path: String? = null)


@Serializable
class VendorResourceListEnvelope(
    @SerialName("data") val resources: MutableList<Vendor>? = null,
    @SerialName("next_page_url") val nextPageUrl: String? = null,
    @SerialName("prev_page_url") val prevPageUrl: String? = null,
    @SerialName("per_page") val perPage: String? = null,
    @SerialName("current_page") val currentPage: Int? = null,
    @SerialName("to") var displayedItemCount: Int? = null,
    @SerialName("total") var totalItemCount: Int? = null,
    @SerialName("path") var path: String? = null)
