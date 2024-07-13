package domain.Enums

enum class Gender {
    MALE,
    FEMALE;

    fun toPath() = when (this) {
        MALE -> "male"
        FEMALE -> "female"
    }

    fun toEventPropertyName() = when (this) {
        MALE -> "male"
        FEMALE -> "female"
    }
}