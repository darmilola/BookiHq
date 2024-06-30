enum class CountryEnum {
    NIGERIA,
    SOUTH_AFRICA;

    fun toPath() = when (this) {
        NIGERIA -> "Nigeria"
        SOUTH_AFRICA -> "SouthAfrica"
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

fun countryList(): ArrayList<String>{
    val countryList = ArrayList<String>()
    countryList.add(CountryEnum.NIGERIA.toPath())
    countryList.add(CountryEnum.SOUTH_AFRICA.toPath())
    return countryList
}
