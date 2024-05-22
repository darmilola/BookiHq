package presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.russhwolf.settings.Settings
import di.initKoin
import domain.Models.BusinessStatusAdsPage
import domain.Models.PlatformNavigator
import domain.Models.VendorStatusModel
import presentation.widgets.BusinessStatusItemWidget
import presentation.widgets.BusinessStatusWidgetUpdated
import presentation.widgets.BusinessWhatsAppStatusWidget

@Composable
fun TestWidgetCompose(platformNavigator: PlatformNavigator) {

    val preferenceSettings: Settings = Settings()

    val modifier =
        Modifier.fillMaxWidth()
            .fillMaxHeight(0.60f)
            .background(color = Color.White)
    val navigator = LocalNavigator.currentOrThrow
    // AnimationEffect
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        val statusList = arrayListOf<VendorStatusModel>()
        val statusModel = VendorStatusModel()
        statusList.add(statusModel)
        statusList.add(statusModel)
        statusList.add(statusModel)
        statusList.add(statusModel)
        val adsPageList: List<BusinessStatusAdsPage> = GetBusinessPageList(statusList)
        BusinessWhatsAppStatusWidget(adsPageList)
    }
}

@Composable
fun GetBusinessPageList(statusList: List<VendorStatusModel>) : List<BusinessStatusAdsPage> {
    val adsList: ArrayList<BusinessStatusAdsPage> = arrayListOf()
    val imageWidget = BusinessStatusItemWidget()
    for (item in statusList){
        val statusAdsPage = BusinessStatusAdsPage(statusWidget = imageWidget, imageUrl = item.imageUrl)
        adsList.add(statusAdsPage)
    }
    return adsList
}

class TestWidgetScreen(val platformNavigator: PlatformNavigator) : Screen {
    @Composable
    override fun Content() {
        initKoin()
        TestWidgetCompose(platformNavigator = platformNavigator)
    }
}

