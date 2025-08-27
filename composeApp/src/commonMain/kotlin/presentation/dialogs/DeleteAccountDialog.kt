package presentation.dialogs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.assignment.moniepointtest.ui.theme.AppTheme
import presentation.components.ButtonComponent
import presentation.widgets.SubtitleTextWidget
import presentations.components.ImageComponent
import presentations.components.TextComponent
import theme.Colors

@Composable
fun DeleteAccountDialog(onCancel: () -> Unit, onConfirmation: () -> Unit) {
    val shouldDismiss = remember {
        mutableStateOf(false)
    }
    if (shouldDismiss.value) return

    AppTheme {
        Dialog(properties = DialogProperties(usePlatformDefaultWidth = false), onDismissRequest = {
            shouldDismiss.value = true
        }) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Colors.lighterPrimaryColor,
                modifier = Modifier.fillMaxWidth(0.85f).height(300.dp)
            ) {
                DeleteAccountDialogContent(onCancel = {
                    onCancel()
                    shouldDismiss.value = true
                }, onConfirmation = {
                    onConfirmation()
                    shouldDismiss.value = true
                })
            }
        }
    }
}

@Composable
fun DeleteAccountDialogContent(onCancel: () -> Unit, onConfirmation: () -> Unit){

    Card(modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        shape = RoundedCornerShape(10.dp),
        elevation = 15.dp, border = BorderStroke((0.5).dp, color = Color.White)
    ) {
        Column(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {

            Box(
                modifier = Modifier.fillMaxWidth().weight(1.5f)
                    .background(color = Color.White), contentAlignment = Alignment.BottomCenter
            ) {
                val modifier = Modifier
                    .padding(top = 2.dp)
                    .size(30.dp)
                Box(modifier = Modifier.size(60.dp).background(color = Colors.lightRedColor, shape = RoundedCornerShape(20.dp)), contentAlignment = Alignment.Center) {
                    ImageComponent(
                        imageModifier = modifier,
                        imageRes = "drawable/delete_icon.png",
                        colorFilter = ColorFilter.tint(color = Colors.redColor)
                    )
                }
            }

            Box(modifier = Modifier.fillMaxWidth().weight(2f)) {
                Column(
                    modifier = Modifier
                        .padding(top = 5.dp, bottom = 10.dp, start = 10.dp, end = 10.dp)
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment  = Alignment.CenterHorizontally,
                ) {

                    val colModifier = Modifier
                        .padding(start = 10.dp, bottom = 10.dp, top = 15.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                    Column(
                        verticalArrangement  = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = colModifier
                    ) {
                        Box(modifier = Modifier.fillMaxWidth().height(30.dp), contentAlignment = Alignment.Center) {
                            SubtitleTextWidget(text = "Delete Account!", textColor = Color.Black, textAlign = TextAlign.Center, fontSize = 25)
                        }

                        Box(modifier = Modifier.fillMaxWidth().height(80.dp), contentAlignment = Alignment.Center) {
                            SubtitleTextWidget(text = "Are you sure you want to\n Delete your Account?", textColor = Colors.grayColor, textAlign = TextAlign.Center, fontSize = 16, lineHeight = 23)
                        }

                    }
                }
            }
            Box(modifier = Modifier.fillMaxWidth().weight(1.5f), contentAlignment = Alignment.Center) {
                DeleteAccountDialogButtonContent(onCancel = { onCancel() }, onConfirmation = {
                    onConfirmation()
                })
            }
        }
    }

}



@Composable
fun DeleteAccountDialogButtonContent(onCancel: () -> Unit, onConfirmation: () -> Unit){
    Row (horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 10.dp, start = 20.dp, end = 20.dp)) {
        ButtonComponent(modifier = Modifier.fillMaxWidth(0.5f), buttonText = "Cancel", colors = ButtonDefaults.buttonColors(backgroundColor = Colors.inputGrayColor), fontSize = 18, shape = RoundedCornerShape(15.dp), textColor = Color.Black, style = MaterialTheme.typography.titleMedium){
            onCancel()
        }
        ButtonComponent(modifier = Modifier.fillMaxWidth().padding(start = 10.dp), buttonText = "Delete", colors = ButtonDefaults.buttonColors(backgroundColor = Colors.redColor), fontSize = 18, shape = RoundedCornerShape(15.dp), textColor = Color.White, style = MaterialTheme.typography.titleMedium){
            onConfirmation()
        }

    }
}