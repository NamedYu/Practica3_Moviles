package cn.yu.practica3

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cn.yu.practica3.ui.theme.Practica3Theme
import cn.yu.practica3.MainActivity


class MainActivity : ComponentActivity() {
    private lateinit var provider: DataProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        provider = DataProvider(this) // mandar Context
        setContent {
            FlickApp(
                lalista = provider.peliculas,
                onPeliClick = {pelicula -> showDetail(pelicula) },
                onBack = {})
        }
    }

    private fun showDetail(pelicula: Pelicula){
        val intent = Intent(this, FlickActivity::class.java).apply {
            putExtra("peliculaID", pelicula.id)
        }
        startActivity(intent)
    }
}