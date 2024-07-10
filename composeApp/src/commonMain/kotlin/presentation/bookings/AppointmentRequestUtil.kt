package presentation.bookings

import domain.Enums.AppointmentType
import domain.Enums.ServiceLocationEnum
import domain.Models.CurrentAppointmentBooking
import domain.Models.User
import domain.Models.Vendor
import domain.bookings.CreateAppointmentRequest

fun getUnSavedAppointment(currentAppointmentBooking: CurrentAppointmentBooking, currentUser: User, currentVendor: Vendor): CreateAppointmentRequest {
        val request = CreateAppointmentRequest(user_id = currentUser.userId!!, vendor_id = currentVendor.vendorId!!, service_id = currentAppointmentBooking.serviceId,
            service_type_id = currentAppointmentBooking.serviceTypeId!!, therapist_id = currentAppointmentBooking.serviceTypeTherapists?.therapistId!!,
            appointmentTime = currentAppointmentBooking.appointmentTime?.id!!, day = currentAppointmentBooking.day, month = currentAppointmentBooking.month, year = currentAppointmentBooking.year, serviceLocation = if (currentAppointmentBooking.isMobileService) ServiceLocationEnum.MOBILE.toPath() else ServiceLocationEnum.SPA.toPath(),
            serviceStatus = currentAppointmentBooking.serviceStatus, appointmentType = AppointmentType.SERVICE.toPath())

        return request
}