package presentation.widgets

import GGSansRegular
import GGSansSemiBold
import theme.styles.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import domain.Enums.Screens
import presentation.viewmodels.MainViewModel
import presentations.components.ImageComponent
import presentations.components.TextComponent

@Composable
fun MobileDeliveryWidget(mainViewModel: MainViewModel) {
    val userInfo = mainViewModel.currentUserInfo.value
    Row(
        modifier = Modifier.fillMaxWidth().height(90.dp).clickable {
            when (mainViewModel.screenNav.value.second) {
                Screens.CART.toPath() -> {
                    mainViewModel.setScreenNav(Pair(Screens.CART.toPath(), Screens.EDIT_PROFILE.toPath()))
                }
                Screens.BOOKING.toPath() -> {
                    mainViewModel.setScreenNav(Pair(Screens.BOOKING.toPath(), Screens.EDIT_PROFILE.toPath()))
                }
                else -> {

                }
            }
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.weight(1F), contentAlignment = Alignment.TopCenter) {
            Box(modifier = Modifier.size(60.dp).background(color = Colors.lightPrimaryColor, shape = RoundedCornerShape(15.dp)), contentAlignment = Alignment.Center) {
                ImageComponent(imageModifier = Modifier.size(24.dp), imageRes = "drawable/location_icon_filled.png", colorFilter = ColorFilter.tint(color = Colors.primaryColor))
            }
        }

        Column(modifier = Modifier.weight(3F)) {
            Box(modifier = Modifier.fillMaxHeight(0.50f), contentAlignment = Alignment.Center) {
                TextComponent(
                    text = userInfo.firstname.toString() + " " + userInfo.lastname.toString(),
                    fontSize = 16,
                    fontFamily = GGSansSemiBold,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Colors.darkPrimary,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Black,
                    lineHeight = 30,
                    textModifier = Modifier
                        .fillMaxHeight(0.50f)
                        .fillMaxWidth()
                )
            }

            Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.Center) {
                TextComponent(
                    textModifier = Modifier.fillMaxWidth().fillMaxHeight(),
                    text = userInfo.address.toString(),
                    fontSize = 15,
                    fontFamily = GGSansRegular,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Color.LightGray,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 23,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Box(modifier = Modifier.weight(1F), contentAlignment =  Alignment.Center) {
            ImageComponent(imageModifier = Modifier.size(24.dp), imageRes = "drawable/chevron_right.png", colorFilter = ColorFilter.tint(color = Colors.primaryColor))
        }
    }
}
