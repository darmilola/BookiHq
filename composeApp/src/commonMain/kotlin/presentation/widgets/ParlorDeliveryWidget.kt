package presentation.widgets

import GGSansRegular
import GGSansSemiBold
import theme.styles.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import domain.Models.Screens
import presentation.viewmodels.MainViewModel
import presentations.components.ImageComponent
import presentations.components.TextComponent

@Composable
fun ParlorDeliveryWidget(mainViewModel: MainViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth().height(90.dp).clickable {
            when (mainViewModel.screenNav.value.second) {
                Screens.BOOKING.toPath() -> {
                    mainViewModel.setScreenNav(Pair(Screens.BOOKING.toPath(), Screens.VENDOR_INFO.toPath()))
                }
                Screens.CART.toPath() -> {
                    mainViewModel.setScreenNav(Pair(Screens.CART.toPath(), Screens.VENDOR_INFO.toPath()))
                }
                else -> {
                    // navigator.current = MainTab(mainViewModel)
                }
            }
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.weight(1F), contentAlignment = Alignment.TopCenter) {
            Box(modifier = Modifier.size(60.dp).background(color = Colors.lightPrimaryColor, shape = RoundedCornerShape(15.dp)), contentAlignment = Alignment.Center) {
                ImageComponent(imageModifier = Modifier.size(30.dp), imageRes = "drawable/spa_logo.png", colorFilter = ColorFilter.tint(color = Colors.primaryColor))
            }
        }

        Column(modifier = Modifier.weight(3F)) {
            TextComponent(
                text = "Kare Beauty Shop And Spa",
                fontSize = 16,
                fontFamily = GGSansSemiBold,
                textStyle =  MaterialTheme.typography.h6,
                textColor = Colors.darkPrimary,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Black,
                lineHeight = 30,
                textModifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth())


            TextComponent(
                textModifier = Modifier.fillMaxWidth().padding(top = 5.dp),
                text = "Lorem ipsum dolor sit amet consectetuer adipiscing Aenean commodo",
                fontSize = 15, fontFamily = GGSansRegular,
                textStyle = MaterialTheme.typography.h6, textColor = Color.LightGray, textAlign = TextAlign.Left,
                fontWeight = FontWeight.Medium,
                lineHeight = 23, maxLines = 1,
                overflow = TextOverflow.Ellipsis)
        }

        Box(modifier = Modifier.weight(1F), contentAlignment =  Alignment.Center) {
            ImageComponent(imageModifier = Modifier.size(24.dp), imageRes = "drawable/chevron_right.png", colorFilter = ColorFilter.tint(color = Colors.primaryColor))
        }
    }
}
