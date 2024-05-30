package utils

import androidx.compose.runtime.snapshots.SnapshotStateList
import domain.Models.OrderItem

fun calculateDiscount(price: Int, discount: Int): Int {
    val reduction: Double = (price - discount).toDouble()
    val ratioDecrease: Double = reduction / price
    return (ratioDecrease * 100).toInt()
}

fun calculateCheckoutSubTotal(orderItems: MutableList<OrderItem>): Int {
   var subtotal: Int = 0
  for (item in orderItems){
      val price = if (item.itemProduct?.isDiscounted!!) item.itemProduct?.discount else item.itemProduct?.productPrice
      val qty = item.itemCount
      val qtyPrice = price?.times(qty)
      subtotal += qtyPrice!!
  }
    return subtotal
}

fun calculateTotal(subtotal: Int, deliveryFee: Int): Int {
    return subtotal + deliveryFee
}
