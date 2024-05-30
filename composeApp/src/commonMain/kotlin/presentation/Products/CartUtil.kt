package presentation.Products

import androidx.compose.runtime.snapshots.SnapshotStateList
import domain.Models.OrderItem
import domain.Models.ServiceLocation
import domain.Models.UnsavedAppointment
import domain.Models.User
import domain.Models.Vendor
import domain.Products.OrderItemRequest
import domain.bookings.CreateAppointmentRequest

fun getUnSavedOrders(unsavedOrders: MutableList<OrderItem>, userId: Int, orderReference: Int): ArrayList<OrderItemRequest> {
    val orderRequestList = arrayListOf<OrderItemRequest>()

    for (item in unsavedOrders){
        val itemRequest = OrderItemRequest(orderReference, userId = userId, productId = item.productId, itemCount = item.itemCount)
        orderRequestList.add(itemRequest)
    }

    return orderRequestList
}