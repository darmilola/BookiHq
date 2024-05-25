package applications.videoplayer

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import domain.Models.VideoStatusViewMeta

@Composable
expect fun VideoPlayer(modifier: Modifier, url: String, videoStatusViewMeta: VideoStatusViewMeta)