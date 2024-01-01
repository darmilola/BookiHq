package screens.authentication

import AppTheme.AppBoldTypography
import AppTheme.AppColors
import GGSansRegular
import Styles.Colors
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import components.TextComponent
import components.TextFieldComponent
import org.jetbrains.compose.resources.ExperimentalResourceApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import components.ButtonComponent
import widgets.PageBackNavWidget
import widgets.PhoneInputWidget
import widgets.SubtitleTextWidget
import widgets.TitleWidget

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
                .padding(top = 40.dp, start = 10.dp)
                .fillMaxWidth()
                .fillMaxHeight(0.87f)
                .background(color = Color(color = 0xFFFBFBFB))


        Column(modifier = rootModifier) {
            Column(modifier = topLayoutModifier) {
                AttachBackIcon()
                EnterPhoneNumberTitle()
                AttachSendCodeDescription()
                PhoneInputWidget()

                ButtonComponent(modifier = buttonStyle, buttonText = "Continue", borderStroke = null, colors = ButtonDefaults.buttonColors(backgroundColor = Colors.primaryColor), fontSize = 18, shape = RoundedCornerShape(25.dp), textColor = Color(color = 0xFFFFFFFF), style = MaterialTheme.typography.h4 ){
                    navigator.replace(AuthenticationComposeScreen(currentScreen = 3))
                }


            }


        }
    }
}
@Composable
fun AttachBackIcon(goToScreen: Int = 0) {
    val navigator = LocalNavigator.currentOrThrow
    PageBackNavWidget(){
        if(goToScreen == -1){
            navigator.replaceAll(WelcomeScreen)
        }
        else {
            navigator.replace(AuthenticationComposeScreen(goToScreen))
        }
    }
}


@Composable
fun EnterPhoneNumberTitle(){
    val rowModifier = Modifier
        .padding(bottom = 10.dp, top = 30.dp)
        .fillMaxWidth()
        .wrapContentHeight()
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top,
        modifier = rowModifier
    ) {
        TitleWidget(title = "Enter Your Phone number", textColor = Colors.primaryColor)
    }
}


@Composable
fun AttachSendCodeDescription() {
    val rowModifier = Modifier
        .padding(top = 30.dp)
        .fillMaxWidth()
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top,
            modifier = rowModifier
        ) {
            SubtitleTextWidget(text = "We'll send a verification code to your\n" +
                    " phone via text message", textAlign = TextAlign.Center)
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


object ContinueWithPhoneScreen : Screen {
    @Composable
    override fun Content() {
        ContinueWithPhoneCompose()
    }
}
