package domain.Enums

enum class ProfileStatus {
    CONNECT_VENDOR,
    COMPLETE_PROFILE,
    DONE;

    fun toPath() = when (this) {
        CONNECT_VENDOR -> "connect_vendor"
        COMPLETE_PROFILE -> "complete_profile"
        DONE -> "done"
    }

    fun toEventPropertyName() = when (this) {
        CONNECT_VENDOR -> "connect_vendor"
        COMPLETE_PROFILE -> "complete_profile"
        DONE -> "done"
    }
}