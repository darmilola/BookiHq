package presentation.dialogs

import theme.Colors
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import presentations.components.TextComponent
@Composable
fun InfoDialog(dialogTitle: String, actionTitle: String, onConfirmation: () -> Unit) {
    val shouldDismiss = remember {
        mutableStateOf(false)
    }
    if (shouldDismiss.value) return

    Dialog(properties = DialogProperties(usePlatformDefaultWidth = false), onDismissRequest = {
       shouldDismiss.value = true
    }) {
        Surface(
            shape = RoundedCornerShape(10.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth(0.40f)
        ) {
            InfoDialogContent(dialogTitle, actionTitle){
                shouldDismiss.value = true
                onConfirmation()
            }
        }
    }
}

@Composable
fun InfoDialogContent(dialogTitle: String, actionTitle: String,onConfirmation: () -> Unit){

    Card(modifier = Modifier.fillMaxWidth(0.30f).height(150.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 15.dp, border = BorderStroke((0.5).dp, color = Colors.primaryColor)
    ) {
        Column(modifier = Modifier.fillMaxWidth().wrapContentHeight(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

            Box(modifier = Modifier.fillMaxWidth().height(120.dp)) {
                TextComponent(
                    textModifier = Modifier.wrapContentWidth().wrapContentHeight()
                        .padding(start = 15.dp, end = 15.dp),
                    text = dialogTitle,
                    fontSize = 20,
                    textStyle = TextStyle(),
                    textColor = Colors.primaryColor,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 23,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Box(modifier = Modifier.fillMaxWidth().height(50.dp), contentAlignment = Alignment.Center) {
            Row (horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {

                TextComponent(
                    textModifier = Modifier.wrapContentWidth().wrapContentHeight().clickable {
                        onConfirmation()
                    }
                    .padding(start = 15.dp, end = 15.dp),
                    text = actionTitle,
                    fontSize = 20,
                    textStyle = TextStyle(),
                    textColor = Color.LightGray,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 23,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }

}

