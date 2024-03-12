package presentation.appointments

import domain.Models.AppointmentItem
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.viewmodels.MainViewModel
import utils.getAppointmentViewHeight
import presentation.widgets.AppointmentWidget

class AppointmentsTab(private val mainViewModel: MainViewModel) : Tab, AppointmentContract.View {

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
                    val appointmentItem4 = AppointmentItem(appointmentType = 4)

                    appointmentList.add(appointmentItem1)
                    appointmentList.add(appointmentItem2)
                    appointmentList.add(appointmentItem3)
                    appointmentList.add(appointmentItem1)
                    appointmentList.add(appointmentItem4)
                    appointmentList.add(appointmentItem2)
                    appointmentList.add(appointmentItem3)
                    appointmentList.add(appointmentItem4)
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

    override fun showError() {
        TODO("Not yet implemented")
    }

    override fun showLce(
        loadingVisible: Boolean,
        contentVisible: Boolean,
        emptyVisible: Boolean,
        errorVisible: Boolean
    ) {
        TODO("Not yet implemented")
    }

    override fun showAppointment() {
        TODO("Not yet implemented")
    }
}