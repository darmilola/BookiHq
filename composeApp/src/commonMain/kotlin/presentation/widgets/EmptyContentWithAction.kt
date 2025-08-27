package presentation.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.assignment.moniepointtest.ui.theme.AppTheme
import presentation.components.ButtonComponent
import presentations.components.ImageComponent
import presentations.components.TextComponent
import theme.styles.Colors

@Composable
fun EmptyContentActionWidget(emptyText: String, actionText: String, onActionClicked: () -> Unit) {
    val columnModifier = Modifier
        .padding(start = 5.dp, end = 5.dp, top = 5.dp, bottom = 5.dp)
        .height(200.dp)

    AppTheme {

        Column(
            modifier = columnModifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
            Box(
                Modifier
                    .clip(CircleShape)
                    .size(80.dp)
                    .background(color = theme.Colors.dashboardOverviewColor),
                contentAlignment = Alignment.Center
            ) {
                ImageComponent(
                    imageModifier = Modifier
                        .size(40.dp),
                    imageRes = "drawable/shopping-bag.png",
                    colorFilter = ColorFilter.tint(color = Colors.darkPrimary)
                )
            }
            TextComponent(
                text = emptyText,
                fontSize = 16,
                textStyle = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                textColor = Colors.darkPrimary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                lineHeight = 30,
                textModifier = modifier
            )

            val buttonStyle = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth(0.60f)
                .background(color = Color.Transparent)
                .height(45.dp)

            ButtonComponent(
                modifier = buttonStyle,
                buttonText = actionText,
                borderStroke = BorderStroke(1.dp, color = Colors.darkPrimary),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                fontSize = 16,
                shape = CircleShape,
                textColor = Colors.darkPrimary,
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
            ) {
                onActionClicked()
            }
        }
    }

}