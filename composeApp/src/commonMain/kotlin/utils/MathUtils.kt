package utils

import domain.Models.OrderItem
import domain.Products.OrderItemRequest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.math.PI
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

fun calculateDiscount(price: Int, discount: Int): Int {
    val reduction: Double = (price - discount).toDouble()
    val ratioDecrease: Double = reduction / price
    return (ratioDecrease * 100).toInt()
}

fun calculateCartCheckoutSubTotal(orderItems: MutableList<OrderItem>): Long {
   var subtotal: Long = 0
  for (item in orderItems){
      val price = item.itemProduct?.productPrice
      val qty = item.itemCount
      val qtyPrice = price?.times(qty)
      subtotal += qtyPrice!!
  }
    return subtotal
}

fun getUnSavedOrdersRequestJson(orders: List<OrderItem>): String {
    val orderRequestList = arrayListOf<String>()
    for (item in orders) {
        val itemRequest = OrderItemRequest(
            productId = item.productId, productName = item.itemProduct?.productName!!,
            productDescription = item.itemProduct!!.productDescription,
            price = item.itemProduct!!.productPrice, itemCount = item.itemCount,
            imageUrl = item.itemProduct!!.productImages.get(0).imageUrl
        )
        val jsonStrRequest = Json.encodeToString(itemRequest)
        orderRequestList.add(jsonStrRequest)
    }
    return orderRequestList.toString()
}

fun calculateTotal(subtotal: Long, deliveryFee: Long): Long {
    return subtotal + deliveryFee
}
 fun getDistanceFromCustomer(userLat: Double, userLong: Double, vendorLat: Double, vendorLong: Double): Double {
    val theta = userLong - vendorLong
    var dist: Double = (sin(deg2rad(userLat)) * sin(deg2rad(vendorLat))
            + (cos(deg2rad(userLat))
            * cos(deg2rad(vendorLat))
            * cos(deg2rad(theta))))
    dist = acos(dist)
    dist = rad2deg(dist)
    dist *= 60 * 1.1515
     dist *= 1.609344
    return dist
}

private fun deg2rad(deg: Double): Double {
    return deg * PI / 180.0
}

private fun rad2deg(rad: Double): Double {
    return rad * 180.0 / PI
}
