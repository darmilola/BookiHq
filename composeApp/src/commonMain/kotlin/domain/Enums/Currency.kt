package domain.Enums

import CountryEnum

enum class Currency {
    NGN,
    USD,
    GHS,
    ZAR,
    KES;

    fun toPath() = when (this) {
        NGN -> "NGN"
        USD -> "USD"
        GHS -> "GHS"
        ZAR -> "ZAR"
        KES -> "KES"
    }

    fun toDisplayUnit() = when (this) {
        NGN -> "₦"
        USD -> "$"
        GHS -> "₵"
        ZAR -> "R"
        KES -> "KSh"
    }
}

fun getDisplayCurrency(country: String): Currency {
    var currency = Currency.USD
    when (country) {
        CountryEnum.NIGERIA.toPath() -> {
            currency = Currency.NGN
        }
        CountryEnum.SOUTH_AFRICA.toPath() -> {
            currency = Currency.ZAR
        }
        CountryEnum.GHANA.toPath() -> {
            currency = Currency.GHS
        }
        CountryEnum.KENYA.toPath() -> {
            currency = Currency.KES
        }
    }
    return currency
}