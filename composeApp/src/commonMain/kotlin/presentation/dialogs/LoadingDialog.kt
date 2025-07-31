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
import presentation.components.IndeterminateCircularProgressBar
import presentations.components.TextComponent

@Composable
fun LoadingDialog(dialogTitle: String) {
        Dialog(properties = DialogProperties(usePlatformDefaultWidth = true), onDismissRequest = {

        }) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color.White,
                modifier = Modifier.fillMaxWidth(0.80f)
            ) {
                LoadingDialogContent(dialogTitle)
            }
        }
    }


@Composable
fun LoadingDialogContent(dialogTitle: String){

    Card(modifier = Modifier.fillMaxWidth(0.20f).height(120.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 15.dp, border = BorderStroke((0.5).dp, color = Colors.primaryColor)
    ) {
        Row(modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(start = 20.dp, end = 20.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {

            IndeterminateCircularProgressBar()

            Box(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
                TextComponent(
                    textModifier = Modifier.wrapContentWidth().wrapContentHeight()
                        .padding(start = 15.dp, end = 15.dp),
                    text = dialogTitle,
                    fontSize = 20,
                    textStyle = TextStyle(),
                    textColor = Color.Black,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 23,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }

}

