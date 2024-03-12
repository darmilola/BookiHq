package domain.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomepageModel (
    @SerialName("user_info") val userInfo: User? = null,
    @SerialName("vendor_info") val vendorInfo: Vendor? = null,
    @SerialName("vendor_status") val vendorStatus: List<VendorStatusModel>? = null,
    @SerialName("vendor_services") val vendorServices: List<Services>? = null,
    @SerialName("vendor_recommendations") val recommendation: List<Recommendation>? = null,
    @SerialName("recent_appointments") val recentAppointment: List<Appointment>? = null,
    @SerialName("popular_products") var popularProducts: List<Product>? = null)
