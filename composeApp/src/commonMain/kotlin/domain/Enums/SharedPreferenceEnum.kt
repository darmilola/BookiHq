package domain.Enums

enum class SharedPreferenceEnum {
    COUNTRY,
    CITY,
    SELECTED_PRODUCT_TYPE,
    IS_SWITCH_VENDOR,
    USER_ID,
    VENDOR_ID,
    AUTH_TYPE,
    AUTH_EMAIL,
    AUTH_PHONE,
    API_KEY;

    fun toPath() = when (this) {
        COUNTRY -> "country"
        CITY -> "city"
        USER_ID -> "profileId"
        VENDOR_ID -> "vendorId"
        AUTH_TYPE -> "authType"
        AUTH_EMAIL -> "authEmail"
        AUTH_PHONE -> "authPhone"
        API_KEY -> "apiKey"
        SELECTED_PRODUCT_TYPE -> "selectedProductType"
        IS_SWITCH_VENDOR -> "is_switch_vendor"
    }

}