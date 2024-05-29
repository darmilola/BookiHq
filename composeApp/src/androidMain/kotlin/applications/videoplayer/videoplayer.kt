package applications.videoplayer

import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.eygraber.compose.placeholder.PlaceholderHighlight
import com.eygraber.compose.placeholder.material3.fade
import com.eygraber.compose.placeholder.material3.placeholder
import domain.Models.VideoStatusViewMeta
import kotlinx.coroutines.flow.collectLatest
import presentations.components.ImageComponent
import theme.Colors

@OptIn(UnstableApi::class) @Composable
actual fun VideoPlayer(modifier: Modifier, url: String, videoStatusViewMeta: VideoStatusViewMeta) {
    // Get the current context
    val context = LocalContext.current

    // Initialize ExoPlayer
    val exoPlayer = ExoPlayer.Builder(context)
        .build()
    val mediaSource = remember(url) {
        MediaItem.fromUri(url)
    }
    exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_DEFAULT
    if (videoStatusViewMeta.currentPage == videoStatusViewMeta.settledPage) {
        exoPlayer.playWhenReady = true
    }

    LaunchedEffect(mediaSource) {
        exoPlayer.setMediaItem(mediaSource)
        exoPlayer.prepare()
    }

    // Manage lifecycle events
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    val playerView = PlayerView(context)
    playerView.player = exoPlayer
    playerView.useController = false
    playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL

    AndroidView(
        factory = { ctx ->
          playerView
        },
        modifier = modifier)
    ShowPlayerViewUI(exoPlayer = exoPlayer)


}

@Composable
fun ShowPlayerViewUI(exoPlayer: ExoPlayer) {

    val isShowingMediaBufferingLayer = remember { mutableStateOf(true) }
    val mMediaIsBuffering = remember { mutableStateOf(false) }

    exoPlayer.addListener(object : Player.Listener {
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            if (isPlaying){
                isShowingMediaBufferingLayer.value = false
                mMediaIsBuffering.value = false
            }
        }

        override fun onIsLoadingChanged(isLoading: Boolean) {
            super.onIsLoadingChanged(isLoading)
            if (isLoading && !exoPlayer.isPlaying){
                isShowingMediaBufferingLayer.value = true
            }
        }

        override fun onPlaybackStateChanged(playbackState: @Player.State Int) {
            when (playbackState) {
                Player.STATE_ENDED -> {
                    exoPlayer.seekTo(0)
                }
                Player.STATE_BUFFERING -> {
                    isShowingMediaBufferingLayer.value = true
                    mMediaIsBuffering.value = true
                }
                Player.STATE_READY -> {
                      if (mMediaIsBuffering.value){
                          isShowingMediaBufferingLayer.value = false
                          mMediaIsBuffering.value = false
                      }
                }
                Player.STATE_IDLE -> {}
            }
        }
     })


    val interactionSource = remember { MutableInteractionSource() }
    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collectLatest { interaction ->
            when (interaction) {
                is PressInteraction.Press -> {
                    if (exoPlayer.isPlaying && !isShowingMediaBufferingLayer.value) {
                        exoPlayer.pause()
                    }
                    else{
                        exoPlayer.play()
                    }
                }
            }
        }
      }

        val bgColor = if (isShowingMediaBufferingLayer.value) Color(0x80ffffff) else  Color.Transparent
        val modifier = Modifier
            .background(color = bgColor)
            .fillMaxSize()

        Box(
            modifier = modifier.clickable(interactionSource = interactionSource, indication = null, onClick = {}),
            contentAlignment = Alignment.Center
        ) {
            if (isShowingMediaBufferingLayer.value) {
                CircularProgressIndicator(
                    modifier = Modifier.size(80.dp),
                    color = Color(color = 0x90004659),
                    strokeWidth = 4.dp)
                }
            }
        }

