package screens.UserProfile.Orders

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import components.ImageComponent
import components.RadioToggleButton
import kotlinx.coroutines.launch
import screens.consultation.AttachAdditionalTextField
import screens.consultation.BookingTitle
import screens.consultation.ConsultLocationToggle
import screens.consultation.WelcomeUser
import screens.consultation.leftTopBarItem
import screens.main.MainViewModel

class OrderDetails() : Screen {
    @OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val rowModifier = Modifier
            .fillMaxWidth()
            .height(40.dp)

        val colModifier = Modifier
            .padding(top = 40.dp, end = 0.dp)
            .fillMaxWidth()
            .fillMaxHeight()

        Column(modifier = colModifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                modifier = rowModifier,
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.weight(1.0f)
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    leftTopBarItem()
                }

            }
            OrderDetailList()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun leftTopBarItem() {
    val navigator = LocalNavigator.currentOrThrow
    val modifier = Modifier
        .padding(start = 15.dp)
        .clickable {
            navigator.pop()
        }
        .size(22.dp)
    ImageComponent(imageModifier = modifier, imageRes = "back_arrow.png", colorFilter = ColorFilter.tint(color = Color.DarkGray))
}

