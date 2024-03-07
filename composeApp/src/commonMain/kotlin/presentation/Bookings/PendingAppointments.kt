package presentation.Bookings

import GGSansSemiBold
import domain.Models.AppointmentItem
import theme.styles.Colors
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.viewmodels.MainViewModel
import utils.getAppointmentViewHeight
import presentation.widgets.AppointmentWidget
import presentation.widgets.PageBackNavWidget
import presentations.components.TextComponent

class PendingAppointmentsTab(private val mainViewModel: MainViewModel) : Tab {
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
        val columnModifier = Modifier
            .padding(top = 5.dp, bottom = 40.dp)
            .fillMaxHeight()
            .fillMaxWidth()


        val rowModifier = Modifier
            .fillMaxWidth()
            .padding(top = 35.dp)
            .height(60.dp)


        Column(
            modifier = columnModifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = rowModifier,
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier.weight(1.0f)
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(start = 10.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    leftTopBarItem(mainViewModel)
                }

                Box(
                    modifier = Modifier.weight(3.0f)
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    BookingTitle()
                }

                Box(
                    modifier = Modifier.weight(1.0f)
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                }

            }

                    val appointmentList = ArrayList<AppointmentItem>()
                    val appointmentItem1 = AppointmentItem(appointmentType = 1)
                    val appointmentItem2 = AppointmentItem(appointmentType = 2)

                    appointmentList.add(appointmentItem1)
                    appointmentList.add(appointmentItem2)

                    PopulateAppointmentScreen(
                        appointmentList = appointmentList,
                        mainViewModel = mainViewModel
                    )

                }

            }


    @Composable
    fun PopulateAppointmentScreen(appointmentList: List<AppointmentItem>, mainViewModel: MainViewModel) {
        LazyColumn(modifier = Modifier.fillMaxWidth().height(getAppointmentViewHeight(appointmentList).dp).padding(top = 20.dp), userScrollEnabled = true) {
            items(appointmentList) {item ->
                AppointmentWidget(itemType = item.appointmentType, mainViewModel)
            }
        }
    }

    @Composable
    fun BookingTitle(){
        TextComponent(
            text = "Pending Appointments",
            fontSize = 20,
            fontFamily = GGSansSemiBold,
            textStyle = TextStyle(),
            textColor = Colors.darkPrimary,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
        )
    }

    @Composable
    fun leftTopBarItem(mainViewModel: MainViewModel) {
        PageBackNavWidget(){
               mainViewModel.setId(0)
        }

    }


}