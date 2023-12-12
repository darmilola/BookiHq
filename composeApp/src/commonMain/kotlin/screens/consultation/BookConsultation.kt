package screens.consultation

import AppTheme.AppBoldTypography
import AppTheme.AppColors
import AppTheme.AppSemiBoldTypography
import GGSansSemiBold
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonColors
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import components.TextComponent


@Composable
fun BookConsultation() {

    val boxModifier =
        Modifier
            .height(350.dp)
            .fillMaxWidth()
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        WelcomeUser()
        ConsultLocationToggle()
        VirtualLocationToggleButton(borderStroke = BorderStroke(1.dp, Color(color = 0xFFF43569)), colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), fontSize = 18, shape = RoundedCornerShape(27.dp), style = MaterialTheme.typography.h4)
        AttachAdditionalTextField()
    }
}


    @Composable
    fun WelcomeUser() {
        val rowModifier = Modifier
            .padding(start = 20.dp, top = 5.dp)
            .fillMaxWidth()
        MaterialTheme(colors = AppColors(), typography = AppSemiBoldTypography()) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = rowModifier
            ) {
                val modifier = Modifier.padding(start = 5.dp)
                TextComponent(
                    text = "Hi ",
                    fontSize = 25,
                    fontFamily = GGSansSemiBold,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Color.DarkGray,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 30
                )
                TextComponent(
                    text = "Jackson",
                    fontSize = 25,
                    fontFamily = GGSansSemiBold,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Color(color = 0xFFFA2D65),
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 30
                )
                TextComponent(
                    text = ",",
                    fontSize = 25,
                    fontFamily = GGSansSemiBold,
                    textStyle = MaterialTheme.typography.h6,
                    textColor = Color.DarkGray,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 30
                )
            }
        }
    }


@Composable
fun VirtualLocationsToggle(){
    val options = listOf("Option1", "Option2", "Option3")
    val selectedOption = remember { mutableStateOf(options[0]) }

    Column {
        options.forEach { option ->
            Row {
                RadioButton(
                    selected = selectedOption.value == option,
                    onClick = { selectedOption.value = option }
                )
                Text(option)
            }
        }
    }
}

@Composable
fun ConsultLocationToggle(){
    val checked by remember { mutableStateOf(false) }

    val tint by animateColorAsState(if (checked) Color.Cyan else Color.Black)
    val textColor = if (checked) Color.White else Color.Cyan

    Column(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 25.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment  = Alignment.CenterHorizontally,
    ) {

        TextComponent(
            text = "How do you want to consult?",
            fontSize = 16,
            fontFamily = GGSansSemiBold,
            textStyle = MaterialTheme.typography.h6,
            textColor = Color.DarkGray,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Black,
            lineHeight = 30,
            textModifier = Modifier
                .fillMaxWidth()
        )

        ConsultLocationToggleButton(borderStroke = BorderStroke(1.dp, Color(color = 0xFFF43569)), colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent), fontSize = 18, shape = RoundedCornerShape(27.dp), style = MaterialTheme.typography.h4)

    }

}
@Composable
fun ConsultLocationToggleButton(borderStroke: BorderStroke?, shape: Shape, colors: ButtonColors, fontSize: Int, style: TextStyle) {

    var isVirtualChecked by remember { mutableStateOf(true) }

    val virtualTint  = if(isVirtualChecked) Color(0xFFFA2D65) else Color.Transparent
    val inPersonTint  =  if(!isVirtualChecked) Color(0xFFFA2D65) else Color.Transparent

    val virtualTextColor = if (isVirtualChecked) Color.White else Color(0xFFFA2D65)

    val inPersonTextColor = if (isVirtualChecked) Color(0xFFFA2D65) else Color.White



    val rowModifier = Modifier
        .padding(top = 15.dp)
        .fillMaxWidth()
        .background(color = Color(0x45C4C4C4), shape = RoundedCornerShape(27.dp))
    MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top,
            modifier = rowModifier
        ) {

            IconToggleButton(
                checked = isVirtualChecked,
                onCheckedChange = { isVirtualChecked = it }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.50f)
                        .height(54.dp)
                        .background(virtualTint, shape = shape),
                    contentAlignment = Alignment.Center,
                ) {
                    TextComponent(
                        text = "Virtual",
                        fontSize = fontSize,
                        textStyle = style,
                        fontFamily = GGSansSemiBold,
                        textColor = virtualTextColor,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Black
                    )
                }
            }

            IconToggleButton(
                checked = !isVirtualChecked,
                onCheckedChange = {
                    isVirtualChecked = !it
                }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .background(inPersonTint, shape = shape),
                    contentAlignment = Alignment.Center,
                ) {
                    TextComponent(
                        text = "In Person",
                        fontSize = fontSize,
                        textStyle = style,
                        fontFamily = GGSansSemiBold,
                        textColor = inPersonTextColor,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }

    }
}


@Composable
fun AdditionalTextField() {
    var note by rememberSaveable { mutableStateOf("") }
    val rowModifier = Modifier
        .wrapContentHeight()
        .fillMaxWidth()

    MaterialTheme(colors = AppColors(), typography = AppSemiBoldTypography()) {
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
                    focusedBorderColor = Color(0xfffa2d65),
                    focusedLabelColor = Color(0xfffa2d65)),
                onValueChange = { note = it },
                label = { Text("Include Additional Note for Discussion") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(color = Color.Transparent)
            )
        }
    }
}


@Composable
fun AttachAdditionalTextField() {
    val checked by remember { mutableStateOf(false) }

    val tint by animateColorAsState(if (checked) Color.Cyan else Color.Black)
    val textColor = if (checked) Color.White else Color.Cyan

    Column(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 20.dp)
            .fillMaxWidth()
            .height(150.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        TextComponent(
            text = "Reason for Consultation?",
            fontSize = 16,
            fontFamily = GGSansSemiBold,
            textStyle = MaterialTheme.typography.h6,
            textColor = Color.DarkGray,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Black,
            textModifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
        )

        AdditionalTextField()

    }

}




@Composable
fun VirtualLocationToggleButton(borderStroke: BorderStroke?, shape: Shape, colors: ButtonColors, fontSize: Int, style: TextStyle) {

    var isZoomChecked by remember { mutableStateOf(true) }
    var isMeetChecked by remember { mutableStateOf(false) }
    var isFaceTime by remember { mutableStateOf(false) }

    val zoomTint  = if(isZoomChecked && !isMeetChecked && !isFaceTime) Color(0xFFFA2D65) else Color(0x45C4C4C4)
    val meetTint  =  if(isMeetChecked && !isZoomChecked && !isFaceTime) Color(0xFFFA2D65) else Color(0x45C4C4C4)
    val faceTimeTint  =  if(isFaceTime && !isZoomChecked && !isMeetChecked) Color(0xFFFA2D65) else Color(0x45C4C4C4)

    val zoomTextColor = if (isZoomChecked) Color.White else Color(0xFFFA2D65)

    val meetTextColor = if (isMeetChecked)Color.White else Color(0xFFFA2D65)

    val faceTimeTextColor = if (isFaceTime)Color.White else Color(0xFFFA2D65)


    Column(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 30.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment  = Alignment.CenterHorizontally,
    ) {

        TextComponent(
            text = "What medium do you prefer?",
            fontSize = 16,
            fontFamily = GGSansSemiBold,
            textStyle = MaterialTheme.typography.h6,
            textColor = Color.DarkGray,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Black,
            lineHeight = 30,
            textModifier = Modifier
                .fillMaxWidth()
        )

    MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxWidth().height(150.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(3) { it ->
                when (it) {
                    0 -> {
                        IconToggleButton(
                            checked = isZoomChecked,
                            onCheckedChange = { it2 ->
                                if(it2 == isZoomChecked || it2 != isFaceTime || it2 != isMeetChecked){
                                    isZoomChecked = it2
                                    isFaceTime = !it2
                                    isMeetChecked = !it2
                                }
                            }
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(top = 10.dp)
                                    .fillMaxWidth()
                                    .height(54.dp)
                                    .background(zoomTint, shape = shape),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = isZoomChecked,
                                    onClick = {

                                    },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = Color.White,
                                        unselectedColor = Color.LightGray
                                    )

                                )
                                TextComponent(
                                    text = "Zoom",
                                    fontSize = fontSize,
                                    textStyle = style,
                                    fontFamily = GGSansSemiBold,
                                    textColor = zoomTextColor,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            }
                        }
                    }

                    1 -> {
                        IconToggleButton(
                            checked = isMeetChecked,
                            onCheckedChange = { it ->
                              if(it == isMeetChecked || it != isFaceTime || it != isZoomChecked) {
                                  isMeetChecked = it
                                  isZoomChecked = !it
                                  isFaceTime = !it
                              }
                            }
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(top = 10.dp)
                                    .fillMaxWidth()
                                    .height(54.dp)
                                    .background(meetTint, shape = shape),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = isMeetChecked,
                                    onClick = {

                                    },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = Color.White,
                                        unselectedColor = Color.LightGray
                                    )

                                )
                                TextComponent(
                                    text = "Meet",
                                    fontSize = fontSize,
                                    textStyle = style,
                                    fontFamily = GGSansSemiBold,
                                    textColor = meetTextColor,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            }
                        }

                    }

                    2 -> {
                        IconToggleButton(
                            checked = isFaceTime,
                            onCheckedChange = { it ->
                                if(it == isFaceTime || it != isZoomChecked || it != isMeetChecked) {
                                    isFaceTime = it
                                    isZoomChecked = !it
                                    isMeetChecked = !it
                                }
                                else{

                                }
                            }
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(start = 5.dp, end = 5.dp, top = 10.dp)
                                    .fillMaxWidth()
                                    .height(54.dp)
                                    .background(faceTimeTint, shape = shape),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = isFaceTime,
                                    onClick = {

                                    },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = Color.White,
                                        unselectedColor = Color.LightGray
                                    )

                                )
                                TextComponent(
                                    text = "FaceTime",
                                    fontSize = fontSize,
                                    textStyle = style,
                                    fontFamily = GGSansSemiBold,
                                    textColor = faceTimeTextColor,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            }
                        }

                    }
                }
            }

        }
    }
 }
}




