package domain.Models

private fun widgetCodeMap(): HashMap<Int, String> {
    val widgetCodeMap: HashMap<Int, String> = HashMap()
    widgetCodeMap[0] = "drawable/hair_and_styling.png"
    widgetCodeMap[1] = "drawable/nails_icon.png"
    widgetCodeMap[2] = "drawable/eyelashes.png"
    widgetCodeMap[3] = "drawable/massage.png"
    widgetCodeMap[4] = "drawable/barber_icon.png"
    widgetCodeMap[5] = "drawable/wax.png"
    widgetCodeMap[6] = "drawable/facial.png"
    widgetCodeMap[7] = "drawable/botox.png"
    widgetCodeMap[8] = "drawable/body.png"
    widgetCodeMap[9] = "drawable/tattoo.png"
    widgetCodeMap[10] = "drawable/makeup.png"
    widgetCodeMap[11] = "drawable/healthcare.png"
    widgetCodeMap[12] = "drawable/discussion.png"


    return widgetCodeMap
}

fun getWidget(widgetCode: Int): String {
    return widgetCodeMap()[widgetCode]!!
}