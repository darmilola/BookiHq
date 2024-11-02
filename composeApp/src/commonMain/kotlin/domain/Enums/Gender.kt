package domain.Enums

enum class Gender {
    MALE,
    FEMALE;

    fun toPath() = when (this) {
        MALE -> "Male"
        FEMALE -> "Female"
    }

    fun toEventPropertyName() = when (this) {
        MALE -> "Male"
        FEMALE -> "Female"
    }
}