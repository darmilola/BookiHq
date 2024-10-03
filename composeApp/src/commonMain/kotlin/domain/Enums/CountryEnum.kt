enum class CountryEnum {
    NIGERIA,
    SOUTH_AFRICA,
    KENYA,
    GHANA;

    fun toPath() = when (this) {
        NIGERIA -> "Nigeria"
        SOUTH_AFRICA -> "South Africa"
        KENYA -> "Kenya"
        GHANA -> "Ghana"
    }

    fun getId() = when (this) {
        NIGERIA -> 0
        SOUTH_AFRICA -> 1
        KENYA -> 2
        GHANA -> 3
    }

    fun toEventPropertyName() = when (this) {
        NIGERIA -> "nigeria"
        SOUTH_AFRICA -> "south_africa"
        KENYA -> "kenya"
        GHANA -> "ghana"
    }
}

fun countryList(): ArrayList<String>{
    val countryList = ArrayList<String>()
    countryList.add(CountryEnum.NIGERIA.toPath())
    countryList.add(CountryEnum.SOUTH_AFRICA.toPath())
    countryList.add(CountryEnum.GHANA.toPath())
    countryList.add(CountryEnum.KENYA.toPath())
    return countryList
}
