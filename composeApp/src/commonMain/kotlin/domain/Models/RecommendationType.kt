package domain.Models

enum class RecommendationType {
    Services,
    Products;

    fun toPath() = when (this) {
        Services -> "services"
        Products -> "products"
    }

    fun toEventPropertyName() = when (this) {
        Services -> "services"
        Products -> "products"
    }
}
