package widgets

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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
fun TrackOrderProgress(modifier: Modifier = Modifier, numberOfSteps: Int, currentProgress: Int) {
    Column (
        modifier = modifier,
    ) {
        for (currentStep in 0..numberOfSteps) {
            when (currentStep) {
                0 -> {
                    TrackOrderStepView(viewHeightMultiplier = 4, currentStep = currentStep, progress = currentProgress, bgColor = Color.Blue)
                }
                numberOfSteps -> {
                    TrackOrderStepView(viewHeightMultiplier = 2, currentStep = currentStep, progress = currentProgress, bgColor = Color.Yellow, isLastStep = true)
                }
                else -> {
                    TrackOrderStepView(viewHeightMultiplier = 2, currentStep = currentStep, progress = currentProgress, bgColor = Color.Cyan)
                }
            }
        }
    }
}

@Composable
fun TrackOrderStepView(viewHeightMultiplier: Int = 0, currentStep: Int, progress: Int, bgColor: Color, isLastStep: Boolean = false){
     Row(modifier = Modifier.height((viewHeightMultiplier * 50).dp).fillMaxWidth()) {
         EnhancedStep(
             modifier = Modifier.width(80.dp),
             isCompete = currentStep < progress,
             isCurrent = currentStep == progress,
             dividerMultiplier = viewHeightMultiplier,
             isLastStep = isLastStep
         )
         Row(modifier = Modifier.fillMaxWidth().fillMaxHeight().background(color = bgColor)) {

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


@Composable
fun EnhancedStep(modifier: Modifier = Modifier, isCompete: Boolean = false, isCurrent: Boolean = false, dividerMultiplier: Int = 1, isLastStep: Boolean = false) {
    val dividerColor = if (isCompete) Color.DarkGray else Color.LightGray
    val dividerHeight = dividerMultiplier * 50


     Column (modifier = modifier.background(color = Color.Transparent),
             verticalArrangement = Arrangement.Center,
             horizontalAlignment = Alignment.CenterHorizontally) {
       if (isCurrent) CurrentDotIndicator(isCompete, isCurrent) else DotIndicator(isCompete, isCurrent)
        //Line
     if(!isLastStep) {
         Divider(
             modifier = Modifier.height(dividerHeight.dp).width(2.dp),
             color = dividerColor,
             thickness = 2.dp
         )
     }
  }
}

@Composable
fun CurrentDotIndicator(isCompete: Boolean, isCurrent: Boolean) {
    val circleColor = if (isCompete || isCurrent) Color.DarkGray else Color.LightGray
    val boxBg = Color(0x35444444)
    Box(
        modifier = Modifier.size(40.dp).background(color = boxBg, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.size(20.dp).background(color = circleColor, shape = CircleShape)) {}
    }
}

@Composable
fun DotIndicator(isCompete: Boolean, isCurrent: Boolean) {
    val circleColor = if (isCompete || isCurrent) Color.DarkGray else Color.LightGray
    Box(modifier = Modifier.size(20.dp).background(color = circleColor, shape = CircleShape)){}
}
