package domain.Enums

import com.russhwolf.settings.Settings

enum class SharedPreferenceEnum {
    COUNTRY,
    CITY,
    PROFILE_ID,
    USER_EMAIL,
    FIRSTNAME,
    VENDOR_EMAIL,
    VENDOR_BUSINESS_LOGO,
    VENDOR_ID,
    VENDOR_WHATSAPP_PHONE,
    AUTH_TYPE,
    AUTH_EMAIL,
    AUTH_PHONE,
    API_KEY,
    LATITUDE,
    LONGITUDE;

    fun toPath() = when (this) {
        COUNTRY -> "country"
        CITY -> "city"
        PROFILE_ID -> "profileId"
        FIRSTNAME -> "firstname"
        VENDOR_ID -> "vendorId"
        VENDOR_WHATSAPP_PHONE -> "whatsappPhone"
        AUTH_TYPE -> "authType"
        AUTH_EMAIL -> "authEmail"
        AUTH_PHONE -> "authPhone"
        API_KEY -> "apiKey"
        LATITUDE -> "latitude"
        LONGITUDE -> "longitude"
        VENDOR_BUSINESS_LOGO -> "businessLogo"
        USER_EMAIL -> "userEmail"
        VENDOR_EMAIL -> "vendorEmail"

    }

}