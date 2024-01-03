package widgets

import GGSansBold
import GGSansRegular
import GGSansSemiBold
import Styles.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import components.ImageComponent
import components.TextComponent

@Composable
fun StepsProgressBar(modifier: Modifier = Modifier, numberOfSteps: Int, currentStep: Int, stepItems: ArrayList<String> = arrayListOf()) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (step in 0..numberOfSteps) {
            if(step == numberOfSteps){
                Step(
                    modifier = Modifier.weight(1F),
                    isCompete = step < currentStep,
                    isCurrentStep = step == currentStep,
                    isLastStep = true,
                    itemTitle = stepItems[step]
                )
            }
            else {
                Step(
                    modifier = Modifier.weight(2.3F),
                    isCompete = step < currentStep,
                    isCurrentStep = step == currentStep,
                    itemTitle = stepItems[step]
                )
            }
        }
    }
}

@Composable
fun TrackOrderProgress(modifier: Modifier = Modifier, numberOfSteps: Int, currentOrderProgress: Int) {
    Column (
        modifier = modifier,
    ) {
        for (currentStep in 0..numberOfSteps) {
            when (currentStep) {
                0 -> {
                    TrackOrderStepView(viewHeightMultiplier = 4, currentStep = currentStep, currentOrderProgress = currentOrderProgress, isLastStep = true)
                }
                numberOfSteps -> {
                    TrackOrderStepView(viewHeightMultiplier = 2, currentStep = currentStep, currentOrderProgress = currentOrderProgress, isInitialStep = true)
                }
                else -> {
                    TrackOrderStepView(viewHeightMultiplier = 2, currentStep = currentStep, currentOrderProgress = currentOrderProgress)
                }
            }
        }
    }
}

@Composable
fun OrderStatusTextView(currentStep: Int, currentOrderProgress: Int){
    var orderStatusTitle = ""
    var orderStatusDescription = ""
    val isCurrentStage: Boolean = currentStep == currentOrderProgress

    when (currentStep) {
        3 -> {
            orderStatusTitle = "Your Payment is Successful"
            orderStatusDescription = "We Just Confirmed your Order"
        }
        2 -> {
            orderStatusTitle = "Your Order is been prepared"
            orderStatusDescription = "We are packing your Order."
        }
        1 -> {
            orderStatusTitle = "Your Order is on the way"
            orderStatusDescription = "All set! we shipped your Order"
        }
    }

    Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
        OrderStatusDate()
        OrderStatusText(orderStatusTitle = orderStatusTitle, orderStatusDescription = orderStatusDescription, isCurrentStage = isCurrentStage)
    }
}


@Composable
fun OrderArrivedView(currentStep: Int, currentOrderProgress: Int){

    val isCurrentStage: Boolean = currentStep == currentOrderProgress

    Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {

        ImageComponent(imageModifier = Modifier.size(100.dp).clickable {
        }, imageRes = "drawable/celebrate_icon.png", colorFilter = ColorFilter.tint(color = Color.DarkGray))

        OrderStatusDate()
        OrderStatusText(orderStatusTitle = "Your Order has arrived", orderStatusDescription = "Thank you for shopping with us. We hope you like the products!", isCurrentStage = isCurrentStage)

    }
}


@Composable
fun OrderStatusText(orderStatusTitle: String, orderStatusDescription: String, isCurrentStage: Boolean = false){
    val titleColor: Color = if (isCurrentStage) Color.DarkGray else Color.Gray
    Column (modifier = Modifier.wrapContentSize()) {

        TextComponent(
            text = orderStatusTitle,
            fontSize = 23,
            fontFamily = GGSansSemiBold,
            textStyle = TextStyle(),
            textColor = titleColor,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Normal,
            lineHeight = 25,
            textModifier = Modifier.wrapContentSize().padding(top = 5.dp))

        TextComponent(
            text = orderStatusDescription,
            fontSize = 20,
            fontFamily = GGSansRegular,
            textStyle = TextStyle(),
            textColor = Color.Gray,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Black,
            lineHeight = 25,
            textModifier = Modifier.wrapContentSize().padding(top = 5.dp))
    }
}

@Composable
fun OrderStatusDate(){
    Row(modifier = Modifier.wrapContentSize()) {
        TextComponent(
            text = "SATURDAY DEC 23, 2023",
            fontSize = 18,
            fontFamily = GGSansRegular,
            textStyle = TextStyle(),
            textColor = Color.Gray,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Black,
            lineHeight = 30,
            textModifier = Modifier.wrapContentSize().padding(top = 5.dp))

        Box(modifier = Modifier.size(5.dp).padding(start = 5.dp, end = 5.dp).background(color = Color.DarkGray, shape = CircleShape))

        TextComponent(
            text = "3:11 PM",
            fontSize = 18,
            fontFamily = GGSansRegular,
            textStyle = TextStyle(),
            textColor = Color.Gray,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Black,
            lineHeight = 30,
            textModifier = Modifier.wrapContentSize().padding(top = 5.dp))
    }
}

@Composable
fun TrackOrderStepView(viewHeightMultiplier: Int = 0, currentStep: Int, currentOrderProgress: Int, isInitialStep: Boolean = false,  isLastStep: Boolean = false){
     Row(modifier = Modifier.height((viewHeightMultiplier * 65).dp).fillMaxWidth()) {
         EnhancedStep(
             modifier = Modifier.width(80.dp),
             isCompete = currentStep > currentOrderProgress,
             isCurrent = currentStep == currentOrderProgress,
             dividerMultiplier = viewHeightMultiplier,
             isLastStep = isInitialStep
         )
         Row(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
            if (currentOrderProgress <= currentStep) {
                if (isInitialStep || !isLastStep) OrderStatusTextView(
                    currentStep,
                    currentOrderProgress
                ) else OrderArrivedView(currentStep, currentOrderProgress)
            }
         }

    }
}

@Composable
fun Step(modifier: Modifier = Modifier, isCompete: Boolean, isCurrentStep: Boolean, isLastStep: Boolean = false, itemTitle: String = "") {
    val color = if (isCompete) Colors.primaryColor else Color.LightGray
    val imageRes: String = if (isCompete) "drawable/check.png" else "drawable/close.png"

    Column(verticalArrangement = Arrangement.Top,
           horizontalAlignment= Alignment.Start,
           modifier = modifier.wrapContentHeight()) {
        Row(
            modifier = Modifier.height(50.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            //Line

            Box(
                modifier = Modifier.size(40.dp).background(color = color, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                ImageComponent(
                    imageModifier = Modifier.size(14.dp),
                    imageRes = imageRes,
                    colorFilter = ColorFilter.tint(color = Color.White)
                )
            }

            if (!isLastStep) Divider(
                modifier = Modifier.padding(start = 5.dp, end = 5.dp),
                color = color,
                thickness = 3.dp
            )
        }
        TextComponent(
            text = itemTitle,
            fontSize = 16,
            fontFamily = GGSansBold,
            textStyle = TextStyle(),
            textColor = Colors.darkPrimary,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            textModifier = Modifier.padding(bottom = 5.dp)
                .alpha(if (isCurrentStep) 1F else 0F)
        )
    }
}



@Composable
fun EnhancedStep(modifier: Modifier = Modifier, isCompete: Boolean = false, isCurrent: Boolean = false, dividerMultiplier: Int = 1, isLastStep: Boolean = false) {
    val dividerColor = if (isCurrent || isCompete) Color.DarkGray else Color.LightGray
    val dividerHeight = dividerMultiplier * 65


     Column (modifier = modifier.background(color = Color.Transparent),
             verticalArrangement = Arrangement.Center,
             horizontalAlignment = Alignment.CenterHorizontally) {
       if (isCurrent) CurrentDotIndicator(isCompete, isCurrent) else DotIndicator(isCompete, isCurrent)
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
