package utils

import domain.Models.OrderItem
import domain.Products.OrderItemRequest
import kotlinx.serialization.SerialName
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun getUnSavedOrders(unsavedOrders: List<OrderItem>): String {
    val orderRequestList = arrayListOf<String>()
    for (item in unsavedOrders){
        val itemRequest = OrderItemRequest(productId = item.productId, productName = item.itemProduct?.productName!!,
            productDescription = item.itemProduct!!.productDescription,
            price = item.itemProduct!!.productPrice ,itemCount = item.itemCount,
            imageUrl = item.itemProduct!!.productImages.get(0).imageUrl)
        val jsonStrRequest = Json.encodeToString(itemRequest)
        orderRequestList.add(jsonStrRequest)
    }
    return orderRequestList.toString()
}