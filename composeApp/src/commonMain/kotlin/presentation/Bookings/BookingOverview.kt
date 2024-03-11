package presentation.Bookings

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import domain.Models.AppointmentItem
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.components.ButtonComponent
import presentation.viewmodels.MainViewModel
import presentation.widgets.AppointmentWidget
import presentation.widgets.PageBackNavWidget
import presentations.components.TextComponent
import theme.styles.Colors
import utils.getAppointmentViewHeight
 @Composable
 fun BookingOverview() {
        val columnModifier = Modifier
            .padding(top = 5.dp, bottom = 30.dp)
            .fillMaxHeight()
            .fillMaxWidth()


        Column(
            modifier = columnModifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val appointmentList = ArrayList<AppointmentItem>()
            val appointmentItem1 = AppointmentItem(appointmentType = 1)
            val appointmentItem2 = AppointmentItem(appointmentType = 2)

            appointmentList.add(appointmentItem1)
            appointmentList.add(appointmentItem2)

            PopulateAppointmentScreen(
                appointmentList = appointmentList,
                mainViewModel = MainViewModel()
            )

            val buttonStyle = Modifier
                .fillMaxWidth(0.80f)
                .height(50.dp)
                .padding(top = 10.dp)

            ButtonComponent(modifier = buttonStyle, buttonText = "Add More Services", borderStroke = BorderStroke(1.dp, Colors.primaryColor), colors = ButtonDefaults.buttonColors(backgroundColor = Color.White), fontSize = 16, shape = RoundedCornerShape(10.dp), textColor = Colors.primaryColor, style = TextStyle()){
           // mainViewModel.setId(0)
        }

        }

    }
    @Composable
    fun PopulateAppointmentScreen(appointmentList: List<AppointmentItem>, mainViewModel: MainViewModel) {
        LazyColumn(modifier = Modifier.fillMaxWidth().height(getAppointmentViewHeight(appointmentList).dp), userScrollEnabled = true) {
            items(appointmentList) {item ->
                AppointmentWidget(itemType = item.appointmentType, mainViewModel)
            }
        }
    }



