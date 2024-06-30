package domain.Enums

enum class CustomerMovementEnum {
    Entry,
    Exit;
    fun toPath() = when (this) {
        Entry -> "entry"
        Exit -> "exit"
    }

    fun toEventPropertyName() = when (this) {
        Entry -> "entry"
        Exit -> "exit"
    }
}
