package domain.Models

enum class ValuesLimit {
        MAX_VALUE,
        MIN_VALUE;
      fun toValue() = when (this) {
            MAX_VALUE -> 2000000000
            MIN_VALUE -> 0
        }
    }
