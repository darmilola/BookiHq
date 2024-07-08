package domain.Enums

enum class ProductType {
    COSMETICS,
    ACCESSORIES;
    fun toPath() = when (this) {
        COSMETICS -> "cosmetics"
        ACCESSORIES -> "accessories"
    }
    fun toEventPropertyName() = when (this) {
        COSMETICS -> "cosmetics"
        ACCESSORIES -> "accessories"
    }
}