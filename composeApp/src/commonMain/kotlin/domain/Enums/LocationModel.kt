package domain.Enums

enum class NGCityList {
    NG1,
    NG2,
    NG3;

    fun toPath() = when (this) {
        NG1 -> "NG1"
        NG2 -> "NG2"
        NG3 -> "NG3"
    }

    fun getId() = when (this) {
        NG1 -> 0
        NG2 -> 1
        NG3 -> 2
    }

    fun toEventPropertyName() = when (this) {
        NG1 -> "NG1"
        NG2 -> "NG2"
        NG3 -> "NG3"
    }
}

enum class SACityList {
    SA1,
    SA2,
    SA3;

    fun toPath() = when (this) {
        SA1 -> "SA1"
        SA2 -> "SA2"
        SA3 -> "SA3"
    }

    fun getId() = when (this) {
        SA1 -> 0
        SA2 -> 1
        SA3 -> 2
    }

    fun toEventPropertyName() = when (this) {
        SA1 -> "SA1"
        SA2 -> "SA2"
        SA3 -> "SA3"
    }
}


enum class GHCityList {
    GH1,
    GH2,
    GH3;

    fun toPath() = when (this) {
        GH1 -> "GH1"
        GH2 -> "GH2"
        GH3 -> "GH3"
    }

    fun getId() = when (this) {
        GH1 -> 0
        GH2 -> 1
        GH3 -> 2
    }

    fun toEventPropertyName() = when (this) {
        GH1 -> "GH1"
        GH2 -> "GH2"
        GH3 -> "GH3"
    }
}

fun nigeriaCities(): HashMap<Int,String>{
    val cityMap = HashMap<Int,String>()
    cityMap[NGCityList.NG1.getId()] = NGCityList.NG1.toPath()
    cityMap[NGCityList.NG2.getId()] = NGCityList.NG2.toPath()
    cityMap[NGCityList.NG3.getId()] = NGCityList.NG3.toPath()

    return cityMap
}

fun southAfricaCities(): HashMap<Int,String>{
    val cityMap = HashMap<Int,String>()
    cityMap[SACityList.SA1.getId()] = SACityList.SA1.toPath()
    cityMap[SACityList.SA2.getId()] = SACityList.SA2.toPath()
    cityMap[SACityList.SA3.getId()] = SACityList.SA3.toPath()

    return cityMap
}

fun ghanaCities(): HashMap<Int,String> {
    val cityMap = HashMap<Int,String>()
    cityMap[GHCityList.GH1.getId()] = GHCityList.GH1.toPath()
    cityMap[GHCityList.GH2.getId()] = GHCityList.GH2.toPath()
    cityMap[GHCityList.GH3.getId()] = GHCityList.GH3.toPath()
    return cityMap
}



fun getCityList(country: String): HashMap<Int,String> {
    val cityMap: HashMap<Int, String> = when(country){
        "Ghana" ->   ghanaCities()
        "Nigeria" ->  nigeriaCities()
        "SouthAfrica" -> southAfricaCities()
        else -> {
            hashMapOf()
        }
    }
    return cityMap
}
