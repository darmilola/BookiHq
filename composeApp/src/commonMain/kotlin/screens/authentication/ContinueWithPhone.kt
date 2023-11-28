package screens.authentication

import AppTheme.AppBoldTypography
import AppTheme.AppColors
import GGSansBold
import GGSansRegular
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import components.ImageComponent
import components.TextComponent
import components.TextFieldComponent
import org.jetbrains.compose.resources.ExperimentalResourceApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import components.ButtonComponent

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterialApi::class)
@Composable
fun ContinueWithPhoneCompose() {
    val viewModel: AuthenticationViewModel = AuthenticationViewModel()
    val authenticationScreenData = viewModel.authenticationScreenData ?: return

    val navigator = LocalNavigator.currentOrThrow


    val  rootModifier =
        Modifier.fillMaxWidth()
            .fillMaxHeight(0.95f)
            .background(color = Color(color = 0xFFFBFBFB))

    val buttonStyle = Modifier
        .padding(top = 50.dp, start = 50.dp, end = 50.dp)
        .fillMaxWidth()
        .height(50.dp)


    MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
        val topLayoutModifier =
            Modifier
                .padding(top = 40.dp)
                .fillMaxWidth()
                .fillMaxHeight(0.87f)
                .background(color = Color(color = 0xFFFBFBFB))


        Column(modifier = rootModifier) {
            Column(modifier = topLayoutModifier) {
                attachBackIcon()
                enterPhoneNumber()
                attachSendCodeText()
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(95.dp)
                    .padding(start = 10.dp, end = 10.dp, top = 30.dp)
                ) {
                    attachDropDownWidget()
                    attachPhoneInput()

                }

               Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .padding(start = 20.dp, end = 20.dp)
                ) {
                    Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.fillMaxWidth(0.20f).padding(end = 5.dp, top = 1.dp))
                    Divider(color = Color.DarkGray, thickness = 2.dp, modifier = Modifier.fillMaxWidth().padding(start = 15.dp))
                }

                ButtonComponent(modifier = buttonStyle, buttonText = "Continue", borderStroke = BorderStroke(1.dp, Color(color = 0xFFF43569)), colors = ButtonDefaults.buttonColors(backgroundColor = Color(color = 0xFFF43569)), fontSize = 18, shape = RoundedCornerShape(25.dp), textColor = Color(color = 0xFFFFFFFF), style = MaterialTheme.typography.h4 ){
                    navigator.replace(AuthenticationComposeScreen(currentScreen = 3))
                }


            }


        }
    }
}

@Composable
fun attachDropDownWidget(){
    val countryCodes = listOf("+234", "+27")

    var countryCodesExpanded = remember { mutableStateOf(false) }

    var selectedIndex = remember { mutableStateOf(0) }

    Column (
        modifier = Modifier
            .height(70.dp)
            .fillMaxWidth(0.20f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        DropDownWidget(
            menuItems = countryCodes,
            menuExpandedState = countryCodesExpanded.value,
            selectedIndex = selectedIndex.value,
            updateMenuExpandStatus = {
                countryCodesExpanded.value = true
            },
            onDismissMenuView = {
                countryCodesExpanded.value = false
            },
            onMenuItemclick = { index->
                selectedIndex.value = index
                countryCodesExpanded.value = false
            }
        )
    }

}

@Composable
fun attachBackIcon(goToScreen: Int = 0) {
    val navigator = LocalNavigator.currentOrThrow
    val modifier = Modifier
        .padding(30.dp)
        .clickable {
            if(goToScreen == -1){
                navigator.replaceAll(WelcomeScreen)
            }
            else {
                navigator.replace(AuthenticationComposeScreen(goToScreen))
            }
        }
        .size(22.dp)
    ImageComponent(imageModifier = modifier, imageRes = "back_arrow.png", colorFilter = ColorFilter.tint(color = Color.DarkGray))
}


@Composable
fun enterPhoneNumber(){
    val rowModifier = Modifier
        .padding(top = 50.dp)
        .fillMaxWidth()
    MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top,
            modifier = rowModifier
        ) {
            val modifier = Modifier.padding(start = 5.dp)
            TextComponent(
                text = "Enter Your Phone number",
                fontSize = 23,
                fontFamily = GGSansBold,
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
fun attachSendCodeText() {
    val rowModifier = Modifier
        .padding(top = 30.dp, start = 10.dp)
        .fillMaxWidth()

    MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top,
            modifier = rowModifier
        ) {
            val modifier = Modifier.padding(start = 5.dp)
            TextComponent(
                text = "We'll send a verification code to your\n phone via text message",
                fontSize = 18,
                fontFamily = GGSansRegular,
                textStyle = MaterialTheme.typography.h6,
                textColor = Color.Gray,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 25
            )
        }
    }
}

@Composable
fun attachPhoneInput() {
    Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()

        ) {

            var text by remember { mutableStateOf(TextFieldValue("")) }
            val inputModifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Transparent)
                .padding(top = 5.dp)
                .fillMaxHeight()
            MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
                val textStyle: TextStyle = TextStyle(
                    fontSize = TextUnit(23f, TextUnitType.Sp),
                    fontFamily = GGSansRegular,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = TextUnit(3f, TextUnitType.Sp)
                )
                TextFieldComponent(
                    text = text,
                    readOnly = false,
                    textStyle = textStyle,
                    modifier = inputModifier,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    onValueChange = { it ->
                        text = it
                    }, isSingleLine = true)
            }

        }

    }


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun attachCountryCodeDropDown() {
    val options = listOf("+234","+27")
    var expanded = remember { mutableStateOf(true) }
    var selectedOptionText = remember { mutableStateOf(options[0]) }

  val menu = ExposedDropdownMenuBox(
        expanded = expanded.value,
        modifier = Modifier

               .fillMaxWidth(0.35f)
               .padding(top = 10.dp, start = 10.dp, end = 10.dp),
        onExpandedChange = {
            expanded.value = !expanded.value
        }
    ) {
      TextField(
          value = selectedOptionText.value,
          readOnly = true,
          onValueChange = { },


          trailingIcon = {
              ExposedDropdownMenuDefaults.TrailingIcon(
                  expanded = expanded.value
              )
          },
          colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
      )
      ExposedDropdownMenu(
          expanded = expanded.value,
          onDismissRequest = {
              expanded.value = false
          }
      ) {
          options.forEach { selectionOption ->
              DropdownMenuItem(
                  onClick = {
                      expanded.value = false
                      selectedOptionText.value = selectionOption
                  }
              ) {
                  Text(text = selectionOption, color = Color.Gray)
              }

          }
      }
  }


}


@OptIn(ExperimentalResourceApi::class)
@Composable
fun DropDownWidget(menuItems: List<String>,
                   menuExpandedState: Boolean,
                   selectedIndex : Int,
                   updateMenuExpandStatus : () -> Unit,
                   onDismissMenuView : () -> Unit,
                   onMenuItemclick : (Int) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopStart)
            .padding(top = 25.dp)
            .clickable(
                onClick = {
                    updateMenuExpandStatus()
                },
            ),
    ) {

        MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth()
            ) {
                val modifier = Modifier.padding(start = 10.dp)

                val textStyle: TextStyle = TextStyle(
                    fontSize = TextUnit(20f, TextUnitType.Sp),
                    fontFamily = GGSansRegular,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = TextUnit(3f, TextUnitType.Sp)
                )

                TextComponent(
                    text = "+27",
                    fontSize = 20,
                    fontFamily = GGSansRegular,
                    textStyle = textStyle,
                    textColor = Color.DarkGray,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold
                )

                val imageModifier = Modifier
                    .size(25.dp)
                    .padding(start = 10.dp, top = 3.dp)
                ImageComponent(
                    imageModifier = imageModifier,
                    imageRes = "chevron_down_icon.png",
                    colorFilter = ColorFilter.tint(color = Color.Gray)
                )

            }
        }
    }

    MaterialTheme(colors = AppColors(), typography = AppBoldTypography()) {

        val textStyle: TextStyle = TextStyle(
            fontSize = TextUnit(20f, TextUnitType.Sp),
            fontFamily = GGSansRegular,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = TextUnit(3f, TextUnitType.Sp)
        )

        DropdownMenu(
            expanded = menuExpandedState,
            onDismissRequest = { onDismissMenuView() },
            modifier = Modifier
                .fillMaxWidth(0.30f)
                .background(MaterialTheme.colors.surface)
        ) {
            menuItems.forEachIndexed { index, title ->
                DropdownMenuItem(
                    onClick = {
                        if (index != 0) {
                            onMenuItemclick(index)
                        }
                    }) {
                    TextComponent(
                        text = title,
                        fontSize = 20,
                        fontFamily = GGSansRegular,
                        textStyle = textStyle,
                        textColor = Color.DarkGray,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}




fun mutableStateOf(b: Boolean) {

}


object ContinueWithPhoneScreen : Screen {
    @Composable
    override fun Content() {
        ContinueWithPhoneCompose()
    }
}
