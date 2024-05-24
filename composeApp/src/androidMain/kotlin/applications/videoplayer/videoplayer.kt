package applications.videoplayer

import android.media.session.PlaybackState
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
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
import presentations.components.ImageComponent

@OptIn(UnstableApi::class) @Composable
actual fun VideoPlayer(modifier: Modifier, url: String) {
    // Get the current context
    val context = LocalContext.current

    // Initialize ExoPlayer
    val exoPlayer = ExoPlayer.Builder(context)
        .build()
    exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_DEFAULT
    //exoPlayer.playWhenReady
    //exoPlayer.playWhenReady = true

    // Create a MediaSource
    println(url)
    val mediaSource = remember(url) {
        MediaItem.fromUri(url)
    }
    exoPlayer.setMediaItem(mediaSource)
    exoPlayer.prepare()



    /*// Set MediaSource to ExoPlayer
    LaunchedEffect(mediaSource) {
        exoPlayer.setMediaItem(mediaSource)
        exoPlayer.prepare()
    }

    // Manage lifecycle events
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }*/

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

/*@Composable
fun populatePlayerViewState(exoPlayer: ExoPlayer, onActionItemIdChanged: (Int) -> Unit): HashMap<Int, Unit> {
    val playerStateMap :HashMap<Int, Unit> = hashMapOf()

    val playButton =  ShowPlayButton(exoPlayer = exoPlayer, onActionPerformed = {
        onActionItemIdChanged(1)
    })

    val pauseButton =  ShowPauseButton(exoPlayer = exoPlayer, onActionPerformed = {
        onActionItemIdChanged(2)
    })

    val loading = ShowLoading()

    val media = ShowMedia()

    playerStateMap[1] = playButton
    playerStateMap[2] = pauseButton
    playerStateMap[3] = loading
    playerStateMap[4] = media

    return playerStateMap

}*/


@Composable
fun ShowPlayerViewUI(exoPlayer: ExoPlayer) {
    val mImageRes = remember { mutableStateOf("drawable/play_icon.png") }
    val mShowMediaActionLayer = remember { mutableStateOf(true) }
    val mMediaIsBuffering = remember { mutableStateOf(false) }

    exoPlayer.addListener(object : Player.Listener { // player listener
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            if (isPlaying){
                //clear ui view
                mShowMediaActionLayer.value = false
                mMediaIsBuffering.value = false
            }
        }

        override fun onIsLoadingChanged(isLoading: Boolean) {
            super.onIsLoadingChanged(isLoading)
            if (isLoading && !exoPlayer.isPlaying){
                // loading the media from the server, playback not yet started
                mImageRes.value = "drawable/loading.gif"
                mShowMediaActionLayer.value = true
            }
            else if (!exoPlayer.isPlaying) {
                //done loading, play media
                mImageRes.value = "drawable/play_icon.png"
                mShowMediaActionLayer.value = true
            }
        }

        // when playback has started
        override fun onPlaybackStateChanged(playbackState: @Player.State Int) {
            when (playbackState) {
                Player.STATE_ENDED -> {
                    exoPlayer.seekTo(0)
                }
                Player.STATE_BUFFERING -> {
                    // waiting for network
                    mImageRes.value = "drawable/loading.gif"
                    mShowMediaActionLayer.value = true
                    mMediaIsBuffering.value = true
                }
                Player.STATE_READY -> {
                      if (mMediaIsBuffering.value){
                          //  clear ui view after buffering
                          mShowMediaActionLayer.value = false
                          mMediaIsBuffering.value = false
                      }

                }
                Player.STATE_IDLE -> {}
            }
        }
    })



 if (mShowMediaActionLayer.value) {
       val bgColor = if (mShowMediaActionLayer.value) Color(0x80ffffff) else  Color.Transparent
        val modifier = Modifier
            .background(color = bgColor)
            .clickable {
                mShowMediaActionLayer.value = true
            }
            .fillMaxSize()
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            ImageComponent(
                imageModifier = Modifier
                    .size(70.dp)
                    .clickable {
                        if (exoPlayer.isPlaying) {
                            exoPlayer.pause()
                            mImageRes.value = "drawable/play_icon.png"
                        } else {
                            exoPlayer.play()
                            mImageRes.value = "drawable/pause_icon.png"
                        }
                    },
                imageRes = mImageRes.value,
                colorFilter = ColorFilter.tint(color = Color(color = 0x90004659))
            )
        }
    }
}





@Composable
fun ShowPlayButton(exoPlayer: ExoPlayer, onActionPerformed:() -> Unit) {
    val modifier = Modifier
        .background(color = Color(0x80ffffff))
        .fillMaxSize()
    Box(modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        ImageComponent(imageModifier = Modifier
            .size(70.dp)
            .clickable {
                if (!exoPlayer.isPlaying) {
                    exoPlayer.play()
                    onActionPerformed()
                }
            }, imageRes = "drawable/play_icon.png", colorFilter = ColorFilter.tint(color = Color(color = 0x90004659)))
    }
}


@Composable
fun ShowPauseButton(exoPlayer: ExoPlayer, onActionPerformed:() -> Unit) {
    val modifier = Modifier
        .background(color = Color(0x80ffffff))
        .fillMaxSize()
    Box(modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        ImageComponent(imageModifier = Modifier
            .size(70.dp)
            .clickable {
                if (exoPlayer.isPlaying) {
                    exoPlayer.pause()
                    onActionPerformed()
                }
            }, imageRes = "drawable/pause_icon.png", colorFilter = ColorFilter.tint(color = Color(color = 0x90004659)))
    }
}

@Composable
fun ShowLoading() {
    val modifier = Modifier
        .background(color = Color(0x80ffffff))
        .fillMaxSize()
    Box(modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        ImageComponent(imageModifier = Modifier
            .size(70.dp), imageRes = "drawable/loading.gif", colorFilter = ColorFilter.tint(color = Color(color = 0x90004659)))
    }
}

@Composable
fun ShowMedia() {
    val modifier = Modifier
        .background(color = Color.Transparent)
        .fillMaxSize()
    Box(modifier = modifier)
}



