package com.example.canciones

import android.media.browse.MediaBrowser.MediaItem
import androidx.compose.ui.graphics.painter.Painter

data class Cancion(val Nombre: String, val Album: String, val Caratula: Painter, val Duracion: String)
