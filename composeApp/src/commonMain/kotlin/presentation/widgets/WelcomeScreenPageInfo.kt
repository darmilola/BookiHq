package presentation.widgets

import GGSansBold
import GGSansRegular
import theme.styles.Colors
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import presentations.components.ImageComponent
import presentations.components.TextComponent

@Composable
fun WelcomeScreenTextWidget() {
    val  modifier =
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()

        Box(modifier = modifier, contentAlignment = Alignment.TopCenter) {
            welcomeScreenTextContent(textStyle = TextStyle(), fontFamily = GGSansBold)
        }
    }


@Composable
fun welcomeScreenTextContent(textStyle: TextStyle, fontFamily: FontFamily? = null) {

    val columnModifier = Modifier
        .fillMaxWidth(0.90f)
        .fillMaxHeight()
        .padding(start = 10.dp)

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = columnModifier
    ) {


        Box(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            contentAlignment = Alignment.TopCenter
        ) {

            val modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
            TextComponent(
                textModifier = modifier,
                text = "Finger Lickin' Good - KFC",
                fontSize = 55,
                fontFamily = GGSansRegular,
                textStyle = TextStyle(),
                textColor = Color.Black,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Black,
                letterSpacing = -1,
                lineHeight = 50
            )

            val iModifier = Modifier
                .padding(top = 100.dp)
                .height(50.dp)
                .width(300.dp)
            ImageComponent(
                imageModifier = iModifier,
                imageRes = "drawable/underline_text.png",
                colorFilter = ColorFilter.tint(color = Colors.primaryColor)
            )
        }

        TextComponent(
            text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum",
            fontSize = 20,
            fontFamily = GGSansRegular,
            textStyle = TextStyle(),
            textColor = Color.DarkGray,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Black,
            lineHeight = 25,
            textModifier = Modifier.fillMaxWidth().padding(top = 10.dp)
        )


    }
}




@Composable
fun WelcomeScreenImageWidget(imageRes: String) {
    val imageModifier =
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()


    val cardModifier =
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()

      Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.65f)) {

          Card (modifier = cardModifier, shape = RoundedCornerShape(35.dp), elevation = 0.dp, backgroundColor = Color(color = 0xFFF0F1F3)) {

              Box(modifier = Modifier.fillMaxHeight().fillMaxWidth(), contentAlignment = Alignment.Center) {

                  Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(), contentAlignment = Alignment.BottomStart) {
                      Box(modifier = Modifier.size(150.dp).offset(x = (-40).dp, y = (20).dp).rotate(30f).background(color = Colors.pinkColor, shape = CircleShape))
                  }

                 Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(), contentAlignment = Alignment.TopStart) {
                      Box(modifier = Modifier.size(150.dp).offset(x = 70.dp, y = 50.dp).border(border = BorderStroke(25.dp, color = Colors.yellowColor), shape = CircleShape))
                  }

                  ImageComponent(imageModifier = imageModifier, imageRes = imageRes)


                  Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(), contentAlignment = Alignment.BottomEnd) {
                      Box(modifier = Modifier.size(120.dp).offset(x = (40).dp, y = (40).dp).rotate(30f).background(color = Colors.primaryColor, shape = CircleShape))
                  }


              }


          }

      }
}

@Composable
fun WelcomeScreenAppLogo() {
    Box(modifier = Modifier.fillMaxWidth(0.90f).fillMaxHeight(0.14f).padding(top = 20.dp), Alignment.CenterStart) {
        ImageComponent(imageModifier = Modifier.height(70.dp).width(170.dp).padding(start = 30.dp), imageRes = "drawable/dummy_logo.png", colorFilter = ColorFilter.tint(color = Colors.primaryColor))
    }
}


@Composable
fun WelcomeScreenPagerContent(imageRes: String) {
    Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start) {
            WelcomeScreenAppLogo()
            WelcomeScreenImageWidget(imageRes)
            WelcomeScreenTextWidget()
        }
}

