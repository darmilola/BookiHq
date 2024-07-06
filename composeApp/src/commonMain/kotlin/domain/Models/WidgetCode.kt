package domain.Models

private fun widgetCodeMap(): HashMap<Int, String> {
    val widgetCodeMap: HashMap<Int, String> = HashMap()
    widgetCodeMap[0] = "drawable/hair_cut.png"
    widgetCodeMap[1] = "drawable/beauty_treatment.png"
    widgetCodeMap[2] = "drawable/hair_cut.png"
    widgetCodeMap[3] = "drawable/hair_dye.png"
    widgetCodeMap[4] = "drawable/spa.png"
    widgetCodeMap[5] = "drawable/waxing.png"
    widgetCodeMap[6] = "drawable/lipstick.png"
    widgetCodeMap[7] = "drawable/massage.png"
    widgetCodeMap[8] = "drawable/spa.png"

    return widgetCodeMap
}

fun getWidget(widgetCode: Int): String {
    return widgetCodeMap()[widgetCode]!!
}