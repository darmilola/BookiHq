package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import domain.Enums.ServiceLocationEnum
import domain.Enums.BookingStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable @Parcelize
data class Appointment(
    @SerialName("id") val appointmentId: Long? = -1, @SerialName("user_id") val userId: Long? = -1, @SerialName("vendor_id") val vendorId: Long = -1,
    @SerialName("serviceLocation") val serviceLocation: String = ServiceLocationEnum.SPA.toPath(), @SerialName("bookingStatus") val bookingStatus: String = BookingStatus.PENDING.toPath(), @SerialName("service_id") var serviceId: Long = -1,
    @SerialName("therapist_id") val therapistId: Long = -1, @SerialName("service_type_id") var serviceTypeId: Long? = -1, @SerialName("appointmentTime") var appointmentTime: Int? = -1, @SerialName("appointmentType") var appointmentType: String = "",
    @SerialName("day") var appointmentDay: Int? = -1, @SerialName("month") var appointmentMonth: Int? = -1, @SerialName("package_id") var packageId: Long = -1,
    @SerialName("package_info") var packageInfo: VendorPackage? = null,
    @SerialName("year") var appointmentYear: Int? = -1, @SerialName("therapist_info") val therapistInfo: TherapistInfo? = null,
    @SerialName("service_type") var serviceTypeItem: ServiceTypeItem? = null, @SerialName("service") var services: Services? = null, @SerialName("time") val platformTime: PlatformTime = PlatformTime(),
    @SerialName("vendor") val vendor: Vendor? = null, @SerialName("customer_info") val customerInfo: User? = null, var isMobileService: Boolean = false, var serviceTypeTherapists: ServiceTypeTherapists? = null, var pendingTime: PlatformTime? = null): Parcelable

data class AppointmentItemUIModel(
    val selectedAppointment: UserAppointment?,
    val appointmentList: List<UserAppointment>
)

data class TherapistAppointmentItemUIModel(
    val selectedAppointment: Appointment?,
    val appointmentList: List<Appointment>
)