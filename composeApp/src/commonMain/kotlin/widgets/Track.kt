package widgets

import AppTheme.AppBoldTypography
import AppTheme.AppColors
import GGSansBold
import GGSansSemiBold
import Models.AvailableSlotsUIModel
import Models.AvailableTherapistUIModel
import Models.TriangleShape
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import components.ImageComponent
import components.TextComponent

@Composable
fun StepsProgressBar(modifier: Modifier = Modifier, numberOfSteps: Int, currentStep: Int) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (step in 0..numberOfSteps) {
            if(step == numberOfSteps){
                Step(
                    isCompete = step < currentStep,
                    isCurrent = step == currentStep,
                    isLastStep = true
                )
            }
            else {
                Step(
                    modifier = Modifier.weight(1F),
                    isCompete = step < currentStep,
                    isCurrent = step == currentStep
                )
            }
        }
    }
}

@Composable
fun Step(modifier: Modifier = Modifier, isCompete: Boolean, isCurrent: Boolean) {
    val color = if (isCompete || isCurrent) Color(color = 0xFFFA2D65) else Color.LightGray
    val innerCircleColor = if (isCompete) Color.Red else Color.LightGray


    Box(modifier = modifier) {

        //Line
        Divider(
            modifier = Modifier.align(Alignment.CenterStart).padding(start = 20.dp, end = 10.dp),
            color = color,
            thickness = 2.dp
        )

        //Circle
        Canvas(modifier = Modifier
            .size(10.dp)
            .align(Alignment.CenterStart)
            .border(
                shape = CircleShape,
                width = 1.dp,
                color = color
            ),
            onDraw = {
                drawCircle(color = innerCircleColor)
            }
        )
    }
}

@Composable
fun Step(modifier: Modifier = Modifier, isCompete: Boolean, isCurrent: Boolean, isLastStep: Boolean) {
    val color = if (isCompete || isCurrent) Color(color = 0xFFFA2D65) else Color.LightGray
    val innerCircleColor = if (isCompete) Color.Red else Color.LightGray


    Box(modifier = modifier) {


        //Circle
        Canvas(modifier = Modifier
            .size(10.dp)
            .align(Alignment.CenterStart)
            .border(
                shape = CircleShape,
                width = 1.dp,
                color = color
            ),
            onDraw = {
                drawCircle(color = innerCircleColor)
            }
        )
    }
}
