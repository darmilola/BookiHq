package domain.Enums

enum class ProductType {
    COSMETICS,
    ACCESSORIES;
    fun toPath() = when (this) {
        COSMETICS -> "cosmetics"
        ACCESSORIES -> "accessories"
    }
    fun toTitle() = when (this) {
        COSMETICS -> "Cosmetics"
        ACCESSORIES -> "Accessories"
    }
}