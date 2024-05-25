package presentations.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource


@OptIn(ExperimentalResourceApi::class)
@Composable
public fun ImageComponent(imageModifier: Modifier, imageRes: String, colorFilter: ColorFilter? = null, contentScale: ContentScale = ContentScale.Crop, isAsync: Boolean = false) {
    if(isAsync){
        Image(
            painter = rememberImagePainter(url = imageRes),
            contentDescription = "Image Component",
            contentScale = contentScale,
            modifier = imageModifier,
            colorFilter = colorFilter)
    }
    else {
        Image(
            painter = painterResource(imageRes),
            contentDescription = "Image Component",
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
        contentDescription = "Image Component",
        contentScale = ContentScale.FillHeight,
        modifier = imageModifier,
        colorFilter = colorFilter
    )
}