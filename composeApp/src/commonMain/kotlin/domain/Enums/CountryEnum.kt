enum class CountryEnum {
    NIGERIA,
    SOUTH_AFRICA,
    KENYA,
    GHANA,
    DEFAULT;

    fun toPath() = when (this) {
        NIGERIA -> "Nigeria"
        SOUTH_AFRICA -> "SouthAfrica"
        KENYA -> "Kenya"
        GHANA -> "Ghana"
        DEFAULT -> "default"
    }

    fun getId() = when (this) {
        NIGERIA -> 1
        SOUTH_AFRICA -> 2
        GHANA -> 3
        KENYA -> 4
        DEFAULT -> -1
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

fun getCountryId(countryName: String): Long {
    val id =  when(countryName){
        CountryEnum.NIGERIA.toPath() -> CountryEnum.NIGERIA.getId()
        CountryEnum.SOUTH_AFRICA.toPath() -> CountryEnum.SOUTH_AFRICA.getId()
        CountryEnum.KENYA.toPath() -> CountryEnum.KENYA.getId()
        CountryEnum.GHANA.toPath() -> CountryEnum.GHANA.getId()
        else -> {
            CountryEnum.DEFAULT.getId()
        }
    }
    return id.toLong()
}
