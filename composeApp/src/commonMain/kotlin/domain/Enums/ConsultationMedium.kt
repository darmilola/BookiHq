package domain.Enums

enum class ConsultationMedium {
    Spa,
    Virtual;
    fun toPath() = when (this) {
        Spa -> "spa"
        Virtual -> "virtual"
    }

    fun toEventPropertyName() = when (this) {
        Spa -> "spa"
        Virtual -> "virtual"
    }
}
