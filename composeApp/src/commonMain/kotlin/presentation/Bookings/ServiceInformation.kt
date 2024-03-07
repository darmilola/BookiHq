package presentation.Bookings

import GGSansRegular
import theme.styles.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import presentation.components.ButtonComponent
import presentation.UserProfile.SwitchVendor.BusinessLogo
import presentation.widgets.DescriptionTextWidget
import presentation.widgets.PageBackNavWidget
import presentation.widgets.TitleWidget
import presentations.components.TextComponent

object ServiceInformationPage : Screen {
    @Composable
    override fun Content() {
        Scaffold(
            topBar = {
                ServiceInfoTitle()
            },
            content = {
                ServiceInfoContent()

             },
            backgroundColor = Color.White,
        )
    }
}

@Composable
fun ServiceInfoTitle(){
    val rowModifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()

    val colModifier = Modifier
        .padding(top = 40.dp, end = 10.dp, start = 10.dp)
        .fillMaxWidth()
        .height(70.dp)

    Column(modifier = colModifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = rowModifier,
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {

            Box(modifier =  Modifier.weight(1.0f)
                .fillMaxWidth()
                .fillMaxHeight(),
                contentAlignment = Alignment.CenterStart) {
                leftTopBarItem()
            }

            Box(modifier =  Modifier.weight(3.0f)
                .fillMaxWidth()
                .fillMaxHeight(),
                contentAlignment = Alignment.Center) {
                TitleWidget(title = "Service Details", textColor = Colors.primaryColor)
            }

            Box(modifier =  Modifier.weight(1.0f)
                .fillMaxWidth(0.20f)
                .fillMaxHeight(),
                contentAlignment = Alignment.Center) {
            }
        }
    }
}

@Composable
fun leftTopBarItem() {
    val navigator = LocalNavigator.currentOrThrow
    PageBackNavWidget() {
        navigator.popUntilRoot()
    }
}





@Composable
fun ServiceInfoContent() {
    val columnModifier = Modifier
        .padding(start = 15.dp, end = 15.dp, top = 10.dp)
        .fillMaxHeight()
        .fillMaxWidth()

    val buttonStyle = Modifier
        .padding(top = 20.dp, start = 30.dp, end = 30.dp)
        .fillMaxWidth()
        .height(50.dp)

    val navigator = LocalNavigator.currentOrThrow

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
         Column(
                modifier = columnModifier,
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
            ) {

             TextComponent(
                 text = "Lorem ipsum dolor sit amet consectetuer adipiscing Aenean",
                 fontSize = 25,
                 fontFamily = GGSansRegular,
                 textStyle = MaterialTheme.typography.h6,
                 textColor = Color.DarkGray,
                 textAlign = TextAlign.Left,
                 fontWeight = FontWeight.Black,
                 lineHeight = 35,
                 textModifier = Modifier.fillMaxWidth().padding(bottom = 15.dp),
             )

                Row(modifier = Modifier.wrapContentHeight().fillMaxWidth().padding(bottom = 10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                    BusinessLogo(size = 45)

                    Box(modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp, top = 10.dp)) {
                        DescriptionTextWidget(fontSize = 20, textColor = Color.DarkGray, text = "This is the Business name")
                    }
                }

               Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(color = Color.LightGray).padding(start = 20.dp, end = 20.dp))

              Box(modifier = Modifier.fillMaxWidth().padding(bottom = 15.dp, top = 15.dp)) {
                  DescriptionTextWidget(fontSize = 20, textColor = Color.DarkGray, text = "A product description is a form of marketing copy used to describe and explain the benefits of your product. In other words, it provides all the information and details of your product on your ecommerce site. These product details can be one sentence, a short paragraph or bulleted. They can be serious, funny or quirk")
              }

             ButtonComponent(modifier = buttonStyle, buttonText = "Continue", borderStroke = null, colors = ButtonDefaults.buttonColors(backgroundColor = Colors.primaryColor), fontSize = 18, shape = RoundedCornerShape(25.dp), textColor = Color(color = 0xFFFFFFFF), style = MaterialTheme.typography.h4 ){
                 navigator.pop()
             }
            }
        }
}


