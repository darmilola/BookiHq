package presentation.widgets
import GGSansRegular
import theme.styles.Colors
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import domain.Models.OrderItem
import presentations.components.ImageComponent
import presentations.components.TextComponent

@Composable
fun IncrementDecrementWidget(){
    var counter by remember { mutableStateOf(1) }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().height(60.dp)

    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(50.dp)
                .width(50.dp)
                .background(color = Color(color = 0xfffa2d65), shape = RoundedCornerShape(10.dp))
        ) {
            TextComponent(
                text = "-",
                fontSize = 30,
                fontFamily = GGSansRegular,
                textStyle = MaterialTheme.typography.h4,
                textColor = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Black,
                textModifier = Modifier
                    .clickable { if(counter > 1)counter -= 1 }
            )
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(50.dp)
                .width(50.dp)
        ) {

            TextComponent(
                text = counter.toString(),
                fontSize = 27,
                fontFamily = GGSansRegular,
                textStyle = MaterialTheme.typography.h4,
                textColor = Color.DarkGray,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Black,
                textModifier = Modifier.padding(start = 10.dp, end = 10.dp)
            )
        }


        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(50.dp)
                .width(50.dp)
                .background(color = Color(color = 0xfffa2d65), shape = RoundedCornerShape(10.dp))
        ) {
            TextComponent(
                text = "+",
                fontSize = 30,
                fontFamily = GGSansRegular,
                textStyle = MaterialTheme.typography.h4,
                textColor = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Black,
                textModifier = Modifier
                    .clickable { counter += 1 }
            )
        }

    }

}



@Composable
fun productItemIncrementDecrementWidget(orderItem: OrderItem, isFromCart: Boolean = false, onItemCountChanged:(OrderItem) -> Unit, onItemRemovedFromCart: (OrderItem) -> Unit){
    var counter by remember { mutableStateOf(orderItem.itemCount) }
    val decrementBorderColor: Color = if(counter == 1) {
        if (isFromCart){
            Colors.pinkColor
        }else {
            Color(0x80CCCCCC)
        }
    } else {
        Colors.primaryColor
    }
    val decrementBg: Color = if(counter == 1) {
        if (isFromCart){
            Colors.lightPinkColor
        }else {
            Color(0x20CCCCCC)
        }
    } else {
        Colors.lightPrimaryColor
    }
    val decrementImgRes: String = if(counter == 1) "drawable/remove_icon.png" else "drawable/minus_icon.png"
    val decrementImgTint: Color = if(counter == 1) {
        if (isFromCart){
            Colors.pinkColor
        }else {
            Color(0x80CCCCCC)
        }
    } else {
        Colors.primaryColor
    }
    val decrementBorderWidth: Int = if(counter == 1) 1 else 2

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.wrapContentWidth().height(50.dp)

    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(45.dp)
                .width(45.dp)
                .border(border = BorderStroke(decrementBorderWidth.dp, decrementBorderColor), shape = RoundedCornerShape(15.dp))
                .background(shape = RoundedCornerShape(15.dp), color = decrementBg)
        ) {

            ImageComponent(imageModifier = Modifier.padding(12.dp).fillMaxSize()
                .clickable { if(counter > 1) { // keep reducing item count
                    counter -= 1
                    orderItem.itemCount = counter
                    onItemCountChanged(orderItem)
                  }
                 else if (isFromCart && counter == 1){
                    onItemRemovedFromCart(orderItem)
                 }
               }, imageRes = decrementImgRes, colorFilter = ColorFilter.tint(color = decrementImgTint))
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .height(50.dp)
                .width(50.dp)
        ) {

            TextComponent(
                text = counter.toString(),
                fontSize = 20,
                fontFamily = GGSansRegular,
                textStyle = MaterialTheme.typography.h4,
                textColor = Colors.darkPrimary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Black,
                textModifier = Modifier.padding(start = 5.dp, end = 5.dp)
            )
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(45.dp)
                .width(45.dp)
                .border(border = BorderStroke(2.dp, Colors.primaryColor), shape = RoundedCornerShape(15.dp))
                .background(shape = RoundedCornerShape(15.dp), color = Colors.lightPrimaryColor)
        ) {

            ImageComponent(imageModifier = Modifier.padding(12.dp).fillMaxSize()
                .clickable {
                    counter += 1
                    orderItem.itemCount = counter
                    onItemCountChanged(orderItem)
               }, imageRes = "drawable/add_icon.png", colorFilter = ColorFilter.tint(color = Colors.primaryColor))
        }

    }

}