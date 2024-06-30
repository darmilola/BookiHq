package domain.Enums

enum class Auth0ConnectionType {
    GOOGLE,
    TWITTER,
    SNAPCHAT,
    EMAIL,
    PHONE;

    fun toPath() = when (this) {
         GOOGLE -> "google-oauth2"
         TWITTER -> "twitter"
         SNAPCHAT -> "snapchat"
         EMAIL -> "email"
         PHONE -> "phone"
    }

    fun toEventPropertyName() = when (this) {
        GOOGLE -> "google-oauth2"
        TWITTER -> "twitter"
        SNAPCHAT -> "snapchat"
        EMAIL -> "email"
        PHONE -> "phone"
    }
}