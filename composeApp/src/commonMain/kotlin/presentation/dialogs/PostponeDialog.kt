package presentation.dialogs

import GGSansSemiBold
import theme.styles.Colors
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import presentation.components.ButtonComponent
import presentation.widgets.AvailableTimeContent
import presentation.widgets.BookingCalendar
import presentation.widgets.NewDateContent
import presentation.widgets.TimeGrid
import presentation.widgets.TitleWidget
import presentation.widgets.buttonContent
import presentations.components.TextComponent

@Composable
fun PostponeDialog(onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit) {
    Dialog( properties = DialogProperties(usePlatformDefaultWidth = false), onDismissRequest = { onDismissRequest() }) {
        Surface(
            shape = RoundedCornerShape(10.dp),
            color = Colors.lighterPrimaryColor,
            modifier = Modifier.fillMaxWidth(0.90f)
        ) {
               Card(modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                    shape = RoundedCornerShape(10.dp),
                    elevation = 15.dp, border = BorderStroke((0.5).dp, color = Colors.primaryColor)
                ) {
                    Column(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {

                        Box(modifier = Modifier.fillMaxWidth().height(90.dp).background(color = Colors.primaryColor), contentAlignment = Alignment.Center) {
                            TitleWidget(title = "Postpone Service", textColor = Color.White)
                        }
                        NewDateContent()
                        AvailableTimeContent()
                        buttonContent(onDismissRequest = {
                            onDismissRequest()
                        }, onConfirmation = {
                            onConfirmation()
                        })
                    }
                }

            }
    }
}
