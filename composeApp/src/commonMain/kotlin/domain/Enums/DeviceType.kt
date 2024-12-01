package domain.Enums

enum class DeviceType {
    IOS,
    ANDROID;
    fun toPath() = when (this) {
        IOS -> "iOS"
        ANDROID -> "Android"
    }
    fun toEventPropertyName() = when (this) {
        IOS -> "iOS"
        ANDROID -> "Android"
    }
}
