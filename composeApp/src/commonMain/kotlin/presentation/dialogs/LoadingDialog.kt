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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
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
import com.assignment.moniepointtest.ui.theme.AppTheme
import presentation.components.IndeterminateCircularProgressBar
import presentation.components.SpinningProgressBar
import presentations.components.TextComponent

@Composable
fun LoadingDialog(dialogTitle: String) {

    AppTheme {
        Dialog(properties = DialogProperties(usePlatformDefaultWidth = false), onDismissRequest = {

        }) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color.White,
                modifier = Modifier.fillMaxWidth(0.70f)
            ) {
                LoadingDialogContent(dialogTitle)
            }
        }
    }

}


@Composable
fun LoadingDialogContent(dialogTitle: String){

    Card(modifier = Modifier.fillMaxWidth(0.70f).height(300.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 15.dp, border = BorderStroke((0.5).dp, color = Colors.primaryColor)
    ) {
        Column(modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(start = 10.dp, end = 10.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

            SpinningProgressBar(modifier = Modifier.size(90.dp))

            Box(modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(top = 30.dp), contentAlignment = Alignment.Center) {
                TextComponent(
                    textModifier = Modifier.wrapContentWidth().wrapContentHeight()
                        .padding(start = 15.dp, end = 15.dp),
                    text = dialogTitle,
                    fontSize = 20,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    textColor = Color.Black,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 23,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }

}
