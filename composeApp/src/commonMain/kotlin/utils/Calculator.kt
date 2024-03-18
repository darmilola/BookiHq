package utils

fun calculateDiscount(price: Int, discount: Int): Int {
    val reduction: Double = (price - discount).toDouble()
    val ratioDecrease: Double = reduction / price
    return (ratioDecrease * 100).toInt()
}