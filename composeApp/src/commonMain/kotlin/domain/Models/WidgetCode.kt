package domain.Models

private fun widgetCodeMap(): HashMap<Int, String> {
    val widgetCodeMap: HashMap<Int, String> = HashMap()
    widgetCodeMap[1] = "drawable/hair_cut.png"
    widgetCodeMap[2] = "drawable/beauty_treatment.png"
    widgetCodeMap[3] = "drawable/nail.png"
    widgetCodeMap[4] = "drawable/hair_dye.png"
    widgetCodeMap[5] = "drawable/spa.png"
    widgetCodeMap[6] = "drawable/waxing.png"
    widgetCodeMap[7] = "drawable/lipstick.png"
    widgetCodeMap[8] = "drawable/massage.png"
    widgetCodeMap[9] = "drawable/spa.png"

    return widgetCodeMap
}

fun getWidget(widgetCode: Int): String {
    return widgetCodeMap()[widgetCode]!!
}