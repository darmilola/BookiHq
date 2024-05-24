package presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@Composable
fun TestWidgetCompose(platformNavigator: PlatformNavigator) {

    val modifier =
        Modifier.fillMaxWidth()
            .fillMaxHeight(0.60f)
            .background(color = Color.White)
    val navigator = LocalNavigator.currentOrThrow
    // AnimationEffect
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
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

        BusinessWhatsAppStatusWidget(statusList)
    }
}
class TestWidgetScreen(val platformNavigator: PlatformNavigator) : Screen {
    @Composable
    override fun Content() {
        initKoin()
        TestWidgetCompose(platformNavigator = platformNavigator)
    }
}

