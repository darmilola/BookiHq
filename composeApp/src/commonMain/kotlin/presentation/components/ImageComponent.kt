package presentations.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import com.seiko.imageloader.rememberImagePainter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource


@OptIn(ExperimentalResourceApi::class)
@Composable
public fun ImageComponent(imageModifier: Modifier, imageRes: String, colorFilter: ColorFilter? = null, contentScale: ContentScale = ContentScale.Crop, isAsync: Boolean = false) {
    if(isAsync){
        Image(
            painter = rememberImagePainter(imageRes),
            contentDescription = "ConnectVendor Logo",
            contentScale = contentScale,
            modifier = imageModifier,
            colorFilter = colorFilter
        )
    }
    else {
        Image(
            painter = painterResource(imageRes),
            contentDescription = "ConnectVendor Logo",
            contentScale = contentScale,
            modifier = imageModifier,
            colorFilter = colorFilter
        )
    }
}


@OptIn(ExperimentalResourceApi::class)
@Composable
public fun ProfileImageComponent(imageModifier: Modifier, imageRes: String, colorFilter: ColorFilter? = null) {
    Image(
        painter = painterResource(imageRes),
        contentDescription = "An Image Component",
        contentScale = ContentScale.FillHeight,
        modifier = imageModifier,
        colorFilter = colorFilter
    )
}