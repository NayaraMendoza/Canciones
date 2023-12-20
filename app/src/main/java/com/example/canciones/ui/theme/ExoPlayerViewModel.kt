package com.example.canciones.ui.theme

import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import android.net.Uri
import androidx.annotation.AnyRes
import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Player.REPEAT_MODE_ALL
import androidx.media3.common.Player.REPEAT_MODE_OFF
import androidx.media3.common.Player.REPEAT_MODE_ONE
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import com.example.canciones.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class ExoPlayerViewModel : ViewModel(){
    private val _exoPlayer : MutableStateFlow<ExoPlayer?> = MutableStateFlow(null)
    val exoPlayer = _exoPlayer.asStateFlow()

    private val _duracion  = MutableStateFlow(0)
    val duracion = _duracion.asStateFlow()

    private val _progreso = MutableStateFlow(0)
    val progreso = _progreso.asStateFlow()

    private val _cancion = MutableStateFlow(0)
    val cancion = _cancion.asStateFlow()

    fun crearExoPlayer(context: Context){
        /*val trackSelector = DefaultTrackSelector(context).apply {
            setParameters(buildUponParameters().setMaxVideoSizeSd())
        }*/
        _exoPlayer.value = ExoPlayer.Builder(context)
            //.setTrackSelector(trackSelector)
            .build()
        _exoPlayer.value!!.prepare()
        _exoPlayer.value!!.playWhenReady = true
        val cancion1 = MediaItem.fromUri(obtenerRuta(context, R.raw.toma_tu_martillo))
        _exoPlayer.value!!.addMediaItem(cancion1)
        val cancion2 = MediaItem.fromUri(obtenerRuta(context,R.raw.la_mejor_cancion))
        _exoPlayer.value!!.addMediaItem(cancion2)
        val cancion3 = MediaItem.fromUri(obtenerRuta(context,R.raw.punto_exe))
        _exoPlayer.value!!.addMediaItem(cancion3)
        val cancion4 = MediaItem.fromUri(obtenerRuta(context,R.raw.chocolate_con_almendras))
        _exoPlayer.value!!.addMediaItem(cancion4)
        val cancion5 = MediaItem.fromUri(obtenerRuta(context,R.raw.mikellino))
        _exoPlayer.value!!.addMediaItem(cancion5)
    }


    fun hacerSonarMusica(context: Context){

        _exoPlayer.value!!.playWhenReady = true

        _exoPlayer.value!!.addListener(object : Player.Listener{
            override fun onPlaybackStateChanged(playbackState: Int) {
                if(playbackState == Player.STATE_READY){

                    _duracion.value = _exoPlayer.value!!.duration.toInt()

                    viewModelScope.launch {
                        while(isActive){
                            _progreso.value = _exoPlayer.value!!.currentPosition.toInt()
                            delay(1000)
                        }

                    }
                }
                else if(playbackState == Player.STATE_BUFFERING){
                }
                else if(playbackState == Player.STATE_ENDED){
                    SiguienteCancion()

                }
                else if(playbackState == Player.STATE_IDLE){
                }

            }
        }
        )


    }

    override fun onCleared() {
        _exoPlayer.value!!.release()
        super.onCleared()
    }

    fun PausarOSeguirMusica() {
        if(!_exoPlayer.value!!.isPlaying ){
            _exoPlayer.value!!.play()
        }else{
            _exoPlayer.value!!.pause()
        }
    }

    fun SiguienteCancion() {
        _exoPlayer.value!!.seekToNextMediaItem()
        /*_exoPlayer.value!!.stop()
        _exoPlayer.value!!.clearMediaItems()

        val mediaItem = MediaItem.fromUri(obtenerRuta(context,_actual.value ))
        _exoPlayer.value!!.setMediaItem(mediaItem)

        _exoPlayer.value!!.prepare()
        _exoPlayer.value!!.playWhenReady = true*/

    }
    fun AnteriorCancion(){
        _exoPlayer.value!!.seekToPreviousMediaItem()
    }
    fun ModoAleatorio(){
        _exoPlayer.value!!.shuffleModeEnabled = !_exoPlayer.value!!.shuffleModeEnabled
    }
    fun Repetir(){
        _exoPlayer.value!!.repeatMode = Player.REPEAT_MODE_OFF
        if (_exoPlayer.value!!.repeatMode == REPEAT_MODE_OFF){
            _exoPlayer.value!!.repeatMode = Player.REPEAT_MODE_ONE
        }
        else if (_exoPlayer.value!!.repeatMode == REPEAT_MODE_ONE){
            _exoPlayer.value!!.repeatMode = Player.REPEAT_MODE_ALL
        }
        else if (_exoPlayer.value!!.repeatMode == REPEAT_MODE_ALL){
            _exoPlayer.value!!.repeatMode = Player.REPEAT_MODE_OFF
        }
    }
}
@Throws(Resources.NotFoundException::class)
fun obtenerRuta(context: Context, @AnyRes resId: Int): Uri {
    val res: Resources = context.resources
    return Uri.parse(
        ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + res.getResourcePackageName(resId)
                + '/' + res.getResourceTypeName(resId)
                + '/' + res.getResourceEntryName(resId)
    )
}