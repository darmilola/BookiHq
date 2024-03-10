package domain.Models

enum class CountryList {
    NIGERIA,
    SOUTH_AFRICA;

    fun toPath() = when (this) {
        NIGERIA -> "Nigeria"
        SOUTH_AFRICA -> "South Africa"
    }

    fun getId() = when (this) {
        NIGERIA -> 0
        SOUTH_AFRICA -> 1
    }

    fun toEventPropertyName() = when (this) {
        NIGERIA -> "nigeria"
        SOUTH_AFRICA -> "south_africa"
    }
}

fun getCountries(): HashMap<Int,String>{
    val countryMap = HashMap<Int,String>()
    countryMap[0] = CountryList.NIGERIA.toPath()
    countryMap[1] = CountryList.SOUTH_AFRICA.toPath()

    return countryMap
}