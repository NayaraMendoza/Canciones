package com.example.canciones

import android.media.browse.MediaBrowser
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.Player
import com.example.canciones.ui.theme.ExoPlayerViewModel

@Composable
fun PantallaCanciones(){
    var vm : ExoPlayerViewModel = viewModel()
    val contexto = LocalContext.current
    val cancion = vm.cancion.collectAsState().value
    val exoplayer = vm.exoPlayer.collectAsState().value
    val duracion = vm.duracion.collectAsState().value
    val progreso = vm.progreso.collectAsState().value

    var canciones = CreaCancion()

    if(exoplayer == null){
        vm.crearExoPlayer(contexto)
    }


    Column {
        Text("Now Playing")
        Text(canciones[cancion].Nombre + " - " + canciones[cancion].Album)
        Image(canciones[cancion].Caratula, contentDescription = null)
        Slider(value = 0F, onValueChange = {})
        Row(horizontalArrangement = Arrangement.SpaceBetween){
            Text(progreso.toString())
            Text(duracion.toString())
        }
        Row{
            Button(onClick = {vm.ModoAleatorio()}){
                if (exoplayer!!.shuffleModeEnabled){
                    Icon(painterResource(id= R.drawable.baseline_shuffle_24), contentDescription = null)
                }
                else Icon(painterResource(id= R.drawable.baseline_shuffle_on_24), contentDescription = null)
            }
            Button(onClick = {vm.AnteriorCancion()}){
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = null)
            }
            Button(onClick = {vm.PausarOSeguirMusica()}){
                if (exoplayer != null && exoplayer!!.isPlaying ){
                    Icon(Icons.Default.PlayArrow, contentDescription = null)
                }
                else Icon(Icons.Default.Pause, contentDescription = null)
            }
            Button(onClick = {vm.SiguienteCancion()}){
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)
            }
            Button(onClick = {vm.Repetir()}){
                if (exoplayer!!.repeatMode == Player.REPEAT_MODE_OFF){
                    Icon(painterResource(id= R.drawable.baseline_repeat_24), contentDescription = null)
                }
                else if (exoplayer!!.repeatMode == Player.REPEAT_MODE_ONE){
                    Icon(painterResource(id= R.drawable.baseline_repeat_one_24), contentDescription = null)
                }
                else if (exoplayer!!.repeatMode == Player.REPEAT_MODE_ALL){
                    Icon(painterResource(id= R.drawable.baseline_repeat_on_24), contentDescription = null)
                }
            }
        }
    }
}
@Composable
fun CreaCancion() : List<Cancion>{
    var listacanciones = mutableListOf<Cancion>()
    val cancion1 = Cancion("Toma tu martillo", "Las Perrerías de Mike", painterResource(id= R.drawable.toma_tu_martillo), "04:27")
    val cancion2 = Cancion("La mejor canción", "Las Perrerías de Mike", painterResource(id= R.drawable.la_mejor_canci_n), "03:53")
    val cancion3 = Cancion("Punto EXE", "Las Perrerías de Mike", painterResource(id= R.drawable.punto_exe), "03:16")
    val cancion4 = Cancion("Chocolate con almendras", "Las Perrerías de Mike", painterResource(id= R.drawable.chocolate_con_almendras), "02:46")
    val cancion5 = Cancion("Mikellino: La fusión de mi corazón", "Las Perrerías de Mike", painterResource(id= R.drawable.mikellino_la_fusi_n_de_mi_coraz_n), "03:12")
    listacanciones.add(cancion1)
    listacanciones.add(cancion2)
    listacanciones.add(cancion3)
    listacanciones.add(cancion4)
    listacanciones.add(cancion5)
    return listacanciones
}