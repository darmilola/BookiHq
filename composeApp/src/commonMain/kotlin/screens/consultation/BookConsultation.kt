package screens.consultation

import AppTheme.AppColors
import AppTheme.AppSemiBoldTypography
import GGSansBold
import GGSansSemiBold
import Styles.Colors
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import components.TextComponent
import components.ToggleButton


@Composable
fun BookConsultation() {

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        WelcomeUser()
        ConsultLocationToggle()
        AttachAdditionalTextField()
    }
}


    @Composable
    fun WelcomeUser() {
        val rowModifier = Modifier
            .padding(start = 20.dp, top = 20.dp)
            .fillMaxWidth()
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = rowModifier
            ) {
                TextComponent(
                    text = "Hi ",
                    fontSize = 25,
                    fontFamily = GGSansBold,
                    textStyle = TextStyle(),
                    textColor = Colors.darkPrimary,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 30
                )
                TextComponent(
                    text = "Jackson",
                    fontSize = 25,
                    fontFamily = GGSansBold,
                    textStyle = TextStyle(),
                    textColor = Colors.primaryColor,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 30
                )
                TextComponent(
                    text = ",",
                    fontSize = 25,
                    fontFamily = GGSansBold,
                    textStyle = TextStyle(),
                    textColor = Colors.darkPrimary,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 30
                )
            }
        }

@Composable
fun ConsultLocationToggle(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment  = Alignment.CenterHorizontally,
    ) {

        TextComponent(
            text = "How do you want to consult?",
            fontSize = 18,
            fontFamily = GGSansSemiBold,
            textStyle = TextStyle(),
            textColor = Colors.darkPrimary,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Black,
            lineHeight = 30,
            textModifier = Modifier
                .fillMaxWidth().padding(start = 15.dp)
        )
        ToggleButton(colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), fontSize = 18, shape = RoundedCornerShape(10.dp), style = TextStyle(), leftText = "Virtual", rightText = "In Person", onLeftClicked = {}, onRightClicked = {})
    }

}

@Composable
fun ConsultationReasonTextField() {
    var note by rememberSaveable { mutableStateOf("") }
    val rowModifier = Modifier
        .wrapContentHeight()
        .padding(top = 5.dp)
        .fillMaxWidth()

        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top,
            modifier = rowModifier
        ) {
            OutlinedTextField(
                value = note,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color.Transparent,
                    unfocusedBorderColor = Color.Gray,
                    unfocusedLabelColor = Color.Gray,
                    focusedBorderColor = Colors.primaryColor,
                    focusedLabelColor = Colors.primaryColor),
                onValueChange = { note = it },
                label = { Text("Include Additional Note for Discussion") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(color = Color.Transparent)
            )
        }
    }


@Composable
fun AttachAdditionalTextField() {
    val checked by remember { mutableStateOf(false) }

    val tint by animateColorAsState(if (checked) Color.Cyan else Color.Black)
    val textColor = if (checked) Color.White else Color.Cyan

    Column(
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp, top = 20.dp)
            .fillMaxWidth()
            .height(150.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        TextComponent(
            text = "Reason for Consultation?",
            fontSize = 18,
            fontFamily = GGSansSemiBold,
            textStyle = TextStyle(),
            textColor = Colors.darkPrimary,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Black,
            lineHeight = 30,
            textModifier = Modifier
                .fillMaxWidth()
        )

        ConsultationReasonTextField()

    }

}

