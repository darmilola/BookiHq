package presentation

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
import domain.Models.VendorStatusModel
import presentation.widgets.BusinessStatusItemWidget
import presentation.widgets.BusinessWhatsAppStatusWidget
import presentations.components.ImageComponent
import theme.styles.Colors

@Composable
fun TestWidgetCompose(platformNavigator: PlatformNavigator) {

    val statusList = arrayListOf<VendorStatusModel>()
    val statusModel1 = VendorStatusModel(statusType = 0)
    val statusModel2 = VendorStatusModel(statusType = 1)
    val statusModel3 = VendorStatusModel(statusType = 0)
    val statusModel4 = VendorStatusModel(statusType = 1)
    val statusModel5 = VendorStatusModel(statusType = 0)
    val statusModel6 = VendorStatusModel(statusType = 1)

    statusList.add(statusModel1)
    statusList.add(statusModel2)
    statusList.add(statusModel3)
    statusList.add(statusModel4)
    statusList.add(statusModel5)
    statusList.add(statusModel6)

    val isStatusExpanded = remember { mutableStateOf(false) }
    val imageRes = if(isStatusExpanded.value) "drawable/collapse_icon.png"  else "drawable/expand_icon.png"
    val modifier =
        Modifier.fillMaxWidth()
            .fillMaxHeight(if (isStatusExpanded.value) 0.80f else 0.60f)
            .background(color = Color.White)
    Box(modifier = modifier, contentAlignment = Alignment.TopCenter) {
        BusinessWhatsAppStatusWidget(statusList)
        Box(modifier = Modifier.fillMaxWidth().height(80.dp).padding(end = 10.dp, top = 20.dp), contentAlignment = Alignment.TopEnd) {
            AttachExpandCollapseIcon(imageRes = imageRes) {
                isStatusExpanded.value = !isStatusExpanded.value
            }
        }
    }
}

@Composable
fun AttachExpandCollapseIcon(imageRes: String, onClick:() -> Unit) {
    Box(modifier = Modifier.size(40.dp).background(color = Color.White, shape = CircleShape), contentAlignment = Alignment.Center) {
        val modifier = Modifier
            .padding(top = 2.dp)
            .clickable {
                onClick()
            }
            .size(20.dp)
        ImageComponent(imageModifier = modifier, imageRes = imageRes, colorFilter = ColorFilter.tint(color = Colors.darkPrimary))
    }
}


class TestWidgetScreen(val platformNavigator: PlatformNavigator) : Screen {
    @Composable
    override fun Content() {
        initKoin()
        TestWidgetCompose(platformNavigator = platformNavigator)
    }
}

