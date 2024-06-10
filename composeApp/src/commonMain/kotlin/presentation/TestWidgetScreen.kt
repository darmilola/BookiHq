package presentation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.russhwolf.settings.Settings
import di.initKoin
import domain.Models.BusinessWhatsAppStatusPage
import domain.Models.PlatformNavigator
import domain.Models.StatusImageModel
import domain.Models.StatusVideoModel
import domain.Models.VendorStatusModel
import presentation.widgets.BusinessStatusItemWidget
import presentation.widgets.BusinessWhatsAppStatusWidget
import presentation.widgets.MultilineInputWidget
import presentations.components.ImageComponent
import theme.styles.Colors

@Composable
fun TestWidgetCompose(platformNavigator: PlatformNavigator) {
    val isStatusExpanded = remember { mutableStateOf(false) }

    val heightChange: Float by animateFloatAsState(targetValue = if (isStatusExpanded.value) 0.80f else 0.60f,
        animationSpec = tween(durationMillis = 600, easing = LinearOutSlowInEasing))


    val modifier =
        Modifier.fillMaxWidth()
            .fillMaxHeight(heightChange)
            .background(color = Color.White)
    Box(modifier = modifier, contentAlignment = Alignment.TopCenter) {
        MultilineInputWidget(viewHeight = 200){

        }
    }
}


class TestWidgetScreen(val platformNavigator: PlatformNavigator) : Screen {
    @Composable
    override fun Content() {
        initKoin()
        TestWidgetCompose(platformNavigator = platformNavigator)
    }
}

