package domain.Enums

enum class ValuesLimit {
        MAX_VALUE,
        MIN_VALUE,
        MAX_SERVICES_SIZE;
      fun toValue() = when (this) {
            MAX_VALUE -> 2000000000
            MIN_VALUE -> 0
            MAX_SERVICES_SIZE -> 24
        }
    }
