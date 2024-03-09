package presentation.dialogs

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import presentations.components.TextComponent
import theme.Colors
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import presentation.components.ButtonComponent
import presentations.components.ImageComponent

@Composable
fun ErrorDialog(dialogTitle: String, actionTitle: String, onConfirmation: () -> Unit) {
    val shouldDismiss = remember {
        mutableStateOf(false)
    }
    if (shouldDismiss.value) return
    Dialog( properties = DialogProperties(usePlatformDefaultWidth = false), onDismissRequest = {
        shouldDismiss.value = true
    }) {
        Surface(
            shape = RoundedCornerShape(10.dp),
            color = Colors.lighterPrimaryColor,
            modifier = Modifier.fillMaxWidth(0.40f).fillMaxHeight(0.45f)
        ) {
            ErrorDialogContent(dialogTitle,actionTitle, onConfirmation = {
                onConfirmation()
                shouldDismiss.value = true
            })
        }
    }
}

@Composable
fun ErrorDialogContent(dialogTitle: String, actionTitle: String,
                                       onConfirmation: () -> Unit){

    Card(modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        shape = RoundedCornerShape(10.dp),
        elevation = 15.dp, border = BorderStroke((0.5).dp, color = Color.White)
    ) {
        Column(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {

            Box(
                modifier = Modifier.fillMaxWidth().weight(1.7f)
                    .background(color = Color.Red), contentAlignment = Alignment.Center
            ) {
                val modifier = Modifier
                    .padding(top = 2.dp)
                    .size(80.dp)
                ImageComponent(imageModifier = modifier, imageRes = "drawable/error_icon.png", colorFilter = ColorFilter.tint(color = Color.White))
            }

            Box(modifier = Modifier.fillMaxWidth().weight(1.8f)) {
                Column(
                    modifier = Modifier
                        .padding(top = 5.dp, bottom = 10.dp, start = 10.dp, end = 10.dp)
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment  = Alignment.CenterHorizontally,
                ) {

                    TextComponent(
                        textModifier = Modifier.wrapContentWidth().wrapContentHeight()
                            .padding(start = 15.dp, end = 15.dp, top = 15.dp),
                        text = dialogTitle,
                        fontSize = 20,
                        textStyle = TextStyle(),
                        textColor = Colors.darkPrimary,
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 23,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )


                    TextComponent(
                        textModifier = Modifier.fillMaxWidth().wrapContentHeight()
                            .padding(start = 15.dp, end = 15.dp, bottom = 10.dp, top = 20.dp),
                        text = "Please fix the error message displayed above to continue",
                        fontSize = 17,
                        textStyle = TextStyle(),
                        textColor = Colors.darkPrimary,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Normal,
                        lineHeight = 23,
                        maxLines = 3)



                }
            }
            Box(modifier = Modifier.fillMaxWidth().weight(1.5f), contentAlignment = Alignment.Center) {
                ErrorDialogButtonContent(actionTitle, onConfirmation = {
                    onConfirmation()
                })
            }
        }
    }

}



@Composable
fun ErrorDialogButtonContent(actionTitle: String, onConfirmation: () -> Unit){
    val buttonStyle = Modifier
        .fillMaxWidth(0.40f)
        .height(50.dp)

    Row (horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 10.dp, start = 20.dp, end = 20.dp)) {
        ButtonComponent(modifier = buttonStyle, buttonText = actionTitle, borderStroke = BorderStroke(1.dp, Colors.primaryColor), colors = ButtonDefaults.buttonColors(backgroundColor = Color.White), fontSize = 18, shape = RoundedCornerShape(10.dp), textColor = Colors.primaryColor, style = TextStyle()){
            onConfirmation()
        }

    }
}


