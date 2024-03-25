package presentation.widgets

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import theme.styles.Colors

@Composable
fun LinearProgressIndicatorWidget() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth().height(2.dp), trackColor = Colors.lightPrimaryColor, color = Colors.primaryColor)
    }
}
