package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import domain.Enums.AppointmentType
import domain.Enums.MeetingStatus
import domain.Enums.ServiceLocationEnum
import domain.Enums.ServiceStatusEnum
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable @Parcelize
data class Appointment(
    @SerialName("id") val appointmentId: Int? = -1, @SerialName("user_id") val userId: Int? = -1, @SerialName("vendor_id") val vendorId: Int = -1,
    @SerialName("serviceLocation") val serviceLocation: String = ServiceLocationEnum.SPA.toPath(), @SerialName("serviceStatus") val serviceStatus: String = ServiceStatusEnum.PENDING.toPath(), @SerialName("service_id") val serviceId: Int = -1,
    @SerialName("therapist_id") val therapistId: Int = -1, @SerialName("service_type_id") val serviceTypeId: Int? = -1, @SerialName("appointmentTime") val appointmentTime: Int? = -1,
    @SerialName("day") val appointmentDay: Int? = -1, @SerialName("month") val appointmentMonth: Int? = -1, @SerialName("appointmentType") val appointmentType: String = AppointmentType.SERVICE.toPath(),
    @SerialName("year") val appointmentYear: Int? = -1, @SerialName("therapist_info") val therapistInfo: TherapistInfo? = null,
    @SerialName("service_type") val serviceTypeItem: ServiceTypeItem? = null, @SerialName("service") val services: Services? = null, @SerialName("time") val platformTime: PlatformTime = PlatformTime(),
    @SerialName("vendor") val vendor: Vendor? = null, @SerialName("customer_info") val customerInfo: User? = null,
    @SerialName("meetingStatus") val meetingStatus: String = MeetingStatus.Pending.toPath(),
    @SerialName("meetingDescription") val meetingDescription: String? = null, @SerialName("meetingId") val meetingId: String? = null): Parcelable

data class AppointmentItemUIModel(
    val selectedAppointment: UserAppointmentsData?,
    val appointmentList: List<UserAppointmentsData>
)