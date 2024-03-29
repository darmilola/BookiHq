package applications.location

import dev.jordond.compass.geocoder.Geocoder
import dev.jordond.compass.geocoder.mobile.mobile

actual fun createGeocoder(): Geocoder {
    return Geocoder.mobile()
}