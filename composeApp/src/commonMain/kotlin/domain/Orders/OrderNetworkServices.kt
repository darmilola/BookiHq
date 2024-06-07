package domain.Orders

import com.badoo.reaktive.single.toSingle
import domain.Models.AppointmentListDataResponse
import domain.Models.OrderListDataResponse
import domain.Models.ServerResponse
import domain.Models.SpecialistAvailabilityResponse
import domain.appointments.DeleteAppointmentRequest
import domain.appointments.GetAppointmentRequest
import domain.appointments.GetSpecialistAppointmentRequest
import domain.appointments.GetSpecialistAvailabilityRequest
import domain.appointments.PostponeAppointmentRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

open class OrderNetworkService(private val apiService: HttpClient) {

    suspend fun getUserOrders(getOrderRequest: GetOrderRequest, nextPage: Int = 1) =
        apiService.post {
            url("/api/v1/orders/get?page=$nextPage")
            /*headers {
                append(HttpHeaders.Authorization, "abc123")
            }*/
            contentType(ContentType.Application.Json)
            setBody(getOrderRequest)
        }.body<OrderListDataResponse>().toSingle()


}