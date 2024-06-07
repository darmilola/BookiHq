package domain.Orders

import com.badoo.reaktive.single.Single
import domain.Models.Appointment
import domain.Models.AppointmentListDataResponse
import domain.Models.OrderListDataResponse
import domain.Models.ServerResponse
import domain.Models.ServiceStatus
import domain.Models.SpecialistAvailabilityResponse
import domain.appointments.AppointmentNetworkService
import domain.appointments.AppointmentRepository
import domain.appointments.DeleteAppointmentRequest
import domain.appointments.GetAppointmentRequest
import domain.appointments.GetSpecialistAppointmentRequest
import domain.appointments.GetSpecialistAvailabilityRequest
import domain.appointments.PostponeAppointmentRequest
import io.ktor.client.HttpClient


class OrderRepositoryImpl(apiService: HttpClient): OrderRepository {
    private val orderNetworkService: OrderNetworkService = OrderNetworkService(apiService)

    override suspend fun getUserOrders(userId: Int, nextPage: Int): Single<OrderListDataResponse> {
        val param = GetOrderRequest(userId)
        return orderNetworkService.getUserOrders(param, nextPage)
    }


}