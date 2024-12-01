enum class CountryEnum {
    NIGERIA,
    SOUTH_AFRICA,
    KENYA,
    GHANA,
    DEFAULT;

    fun toPath() = when (this) {
        NIGERIA -> "Nigeria"
        SOUTH_AFRICA -> "South Africa"
        KENYA -> "Kenya"
        GHANA -> "Ghana"
        DEFAULT -> "default"
    }

    fun getId() = when (this) {
        NIGERIA -> 0
        SOUTH_AFRICA -> 1
        KENYA -> 2
        GHANA -> 3
        DEFAULT -> 4
    }

    fun getCode() = when (this) {
        NIGERIA -> "+234"
        SOUTH_AFRICA -> "+27"
        KENYA -> "+254"
        GHANA -> "+233"
        DEFAULT -> "+0"
    }

    fun toEventPropertyName() = when (this) {
        NIGERIA -> "nigeria"
        SOUTH_AFRICA -> "south_africa"
        KENYA -> "kenya"
        GHANA -> "ghana"
        DEFAULT -> "default"
    }
}

fun countryList(): ArrayList<String>{
    val countryList = ArrayList<String>()
    countryList.add(CountryEnum.NIGERIA.toPath())
    /*countryList.add(CountryEnum.SOUTH_AFRICA.toPath())
    countryList.add(CountryEnum.GHANA.toPath())
    countryList.add(CountryEnum.KENYA.toPath())*/
    return countryList
}
