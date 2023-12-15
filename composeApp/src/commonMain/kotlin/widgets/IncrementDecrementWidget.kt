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

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().height(60.dp)
    ) {
        var counter by remember { mutableStateOf(10) }
        TextComponent(
            text = "+6",
            fontSize = 25,
            fontFamily = GGSansRegular,
            textStyle = TextStyle(fontFamily = GGSansRegular),
            textColor = Color.White,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Normal,
            textModifier = Modifier
                .height(50.dp)
                .width(50.dp)
                .clickable { counter -= 1 }
        )

        TextComponent(
            text = counter.toString(),
            fontSize = 16,
            fontFamily = GGSansSemiBold,
            textStyle = MaterialTheme.typography.h6,
            textColor = Color.DarkGray,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Black,
            textModifier = Modifier.padding(start = 10.dp, end = 10.dp))

        TextComponent(
            text = "+",
            fontSize = 22,
            fontFamily = GGSansRegular,
            textStyle = TextStyle(fontFamily = GGSansSemiBold, letterSpacing = TextUnit(0.5F, TextUnitType.Sp)),
            textColor = Color.DarkGray,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            textModifier = Modifier.height(40.dp).width(40.dp).clickable {
                counter += 1
            }
        )

    }

}