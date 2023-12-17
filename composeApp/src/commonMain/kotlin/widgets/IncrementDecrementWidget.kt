package widgets
import GGSansRegular
import GGSansSemiBold
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import components.ButtonComponent
import components.TextComponent

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