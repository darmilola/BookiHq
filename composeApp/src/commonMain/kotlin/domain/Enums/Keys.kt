package domain.Enums

enum class Keys {
    PAYSTACK_PUBLIC_KEY;
    fun toPath() = when (this) {
        PAYSTACK_PUBLIC_KEY -> "pk_test_bf395e89a315198929ddca163fafecf64899d524"
    }
}