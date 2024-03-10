package domain.Models

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

fun getNGCityList(): HashMap<Int,String>{
    val cityMap = HashMap<Int,String>()
    cityMap[0] = NGCityList.NG1.toPath()
    cityMap[1] = NGCityList.NG2.toPath()
    cityMap[2] = NGCityList.NG3.toPath()

    return cityMap
}

fun getSACityList(): HashMap<Int,String>{
    val cityMap = HashMap<Int,String>()
    cityMap[0] = SACityList.SA1.toPath()
    cityMap[1] = SACityList.SA2.toPath()
    cityMap[2] = SACityList.SA3.toPath()

    return cityMap
}