package domain.Enums

enum class VendorEnum {
    DEFAULT_VENDOR_ID;

    fun toPath() = when (this) {
        DEFAULT_VENDOR_ID -> -1L
    }
}