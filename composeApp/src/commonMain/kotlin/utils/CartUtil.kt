package utils

import domain.Models.OrderItem
import domain.Products.OrderItemRequest

fun getUnSavedOrders(unsavedOrders: MutableList<OrderItem>, userId: Long, orderReference: Int): ArrayList<OrderItemRequest> {
    val orderRequestList = arrayListOf<OrderItemRequest>()

    for (item in unsavedOrders){
        val itemRequest = OrderItemRequest(orderReference, userId = userId, productId = item.productId, itemCount = item.itemCount)
        orderRequestList.add(itemRequest)
    }

    return orderRequestList
}