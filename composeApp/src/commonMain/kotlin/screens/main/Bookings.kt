package screens.main

import AppTheme.AppBoldTypography
import AppTheme.AppColors
import AppTheme.AppRegularTypography
import GGSansBold
import GGSansSemiBold
import Models.AppointmentItem
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import components.ButtonComponent
import components.ImageComponent
import components.TextComponent
import components.ToggleButton
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import screens.Bookings.BookingItemCard
import utils.getAppointmentViewHeight
import widgets.AppointmentWidget

class BookingsTab(private val mainViewModel: MainViewModel) : Tab {

    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() {
            val title = "Bookings"
            val icon = painterResource("calender_icon_semi.png")



            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        mainViewModel.setTitle(options.title.toString())
        val columnModifier = Modifier
            .padding(top = 5.dp)
            .fillMaxHeight()
            .fillMaxWidth()

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = columnModifier
            ) {
                Column(
                    Modifier
                        .padding(bottom = 85.dp)
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .background(color = Color.White)
                ) {


                    val appointmentList = ArrayList<AppointmentItem>()

                    val appointmentItem1 = AppointmentItem(appointmentType = 1)
                    val appointmentItem2 = AppointmentItem(appointmentType = 2)
                    val appointmentItem3 = AppointmentItem(appointmentType = 3)

                    appointmentList.add(appointmentItem1)
                    appointmentList.add(appointmentItem2)
                    appointmentList.add(appointmentItem3)
                    appointmentList.add(appointmentItem1)
                    appointmentList.add(appointmentItem2)
                    appointmentList.add(appointmentItem3)
                    appointmentList.add(appointmentItem1)
                    appointmentList.add(appointmentItem2)

                    PopulateAppointmentScreen(appointmentList = appointmentList, mainViewModel = mainViewModel)

                }

            }
        }


    @Composable
    fun PopulateAppointmentScreen(appointmentList: List<AppointmentItem>, mainViewModel: MainViewModel) {
        LazyColumn(modifier = Modifier.fillMaxWidth().height(getAppointmentViewHeight(appointmentList).dp), userScrollEnabled = true) {
            items(appointmentList) {item ->
                AppointmentWidget(itemType = item.appointmentType)
            }
        }
    }
}