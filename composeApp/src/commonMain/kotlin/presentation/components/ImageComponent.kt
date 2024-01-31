package presentation.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource


@OptIn(ExperimentalResourceApi::class)
@Composable
public fun ImageComponent(imageModifier: Modifier, imageRes: String, colorFilter: ColorFilter? = null, contentScale: ContentScale = ContentScale.Crop) {
    Image(
        painter = painterResource(imageRes),
        contentDescription = "An Image Component",
        contentScale = contentScale,
        modifier = imageModifier,
        colorFilter = colorFilter
    )
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