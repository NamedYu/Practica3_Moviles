package cn.yu.practica3

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.yu.practica3.ui.theme.Practica3Theme
import coil.compose.rememberImagePainter // 使用 Coil 来加载网络图片
import java.nio.file.Paths
import java.util.Spliterator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlickDetailPage(
    modifier: Modifier = Modifier,
    pelicula: Pelicula,
    onBack: () -> Unit
){
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {IconButton(onClick = {onBack()}){
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                } }
            )
        }

    ) { innerPadding ->
        FlickDetail(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            pelicula
        )
    }
}

@Composable
fun FlickDetail(
    modifier: Modifier = Modifier,
    pelicula: Pelicula
){
    Box(modifier = Modifier.fillMaxHeight().fillMaxWidth()){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(top = 15.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top

        ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Card(
                modifier = Modifier
                    .padding(8.dp)

            ) {
                Box(
                    modifier = Modifier
                        .size(150.dp, 200.dp)
                        .fillMaxWidth()
                ) {
                    // 使用 Coil 加载图像
                    val imageUrl = pelicula.image?.original ?: "your_default_image_url" // 设置默认图像 URL
                    Image(
                        painter = rememberImagePainter(imageUrl),
                        contentDescription = pelicula.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop // Cortar imagen
                    )
                    //Rating
                    pelicula.rating?.let {
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(4.dp)
                                .background(
                                    Color(0xFFBBE1FA),
                                    shape = RoundedCornerShape(12.dp, 12.dp, 12.dp, 12.dp)
                                )
                                .padding(horizontal = 6.dp, vertical = 2.dp) // 内部文字的边距
                        ) {
                            Text(
                                text = "$it",
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
//                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ){
                Text(
                    text = pelicula.name ?: "Unknown",
                    style = MaterialTheme.typography.titleLarge
                    .copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Text(
                    text = buildAnnotatedString {
                        pushStyle(SpanStyle(fontSize = 12.sp))
                        pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                        append("Genres: ")
                        pop()
                        append(pelicula.genres?.joinToString(separator = ","))
                    },
//                    style = MaterialTheme.typography.bodySmall
//                        .copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .padding(vertical = 1.dp)
                )
                Text(
                    text = buildAnnotatedString {
                        pushStyle(SpanStyle(fontSize = 12.sp))
                        pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                        append("Premiered: ")
                        pop()
                        append(pelicula.premiered)
                    },
//                    style = MaterialTheme.typography.titleLarge
//                        .copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .padding(vertical = 1.dp)
                )
                Text(
                    text = buildAnnotatedString {
                        pushStyle(SpanStyle(fontSize = 12.sp))
                        pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                        append("Country: ")
                        pop()
                        append(pelicula.network?.country?.name+","+pelicula.network?.country?.code)
                    },
//                    style = MaterialTheme.typography.titleLarge
//                        .copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .padding(vertical = 1.dp)
                )
                Text(
                    text = buildAnnotatedString {
                        pushStyle(SpanStyle(fontSize = 12.sp))
                        pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                        append("Lenguage: ")
                        pop()
                        append(pelicula.language)
                    },
//                    style = MaterialTheme.typography.titleLarge
//                        .copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .padding(vertical = 1.dp)
                )
            }
        }
        Divider(modifier = Modifier.padding(vertical = 15.dp))
        Text(
            text = "Summary",
            style = MaterialTheme.typography.bodyLarge
                .copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        val texto = pelicula.summary?.replace("<p>", "")
            ?.replace("</p>", "")
            ?.replace("<b>", "")
            ?.replace("</b>", "")
        Text(
            text = texto?:"NULL",

            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(vertical = 10.dp)
        )

    }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentAlignment = Alignment.BottomEnd
        ) {
            ShareButton(pelicula) // Agregar share button
            modifier.padding()
        }
    }
}

@Composable
fun ShareButton(pelicula: Pelicula){
    val context = LocalContext.current
    val shareText = "Check out this link: ${pelicula.officialSite}"

    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, shareText)
        type = "text/plain"
    }

    IconButton(
        onClick = {
            context.startActivity(Intent.createChooser(shareIntent, null))
        },
        modifier = Modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)
            .padding(12.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Share,
            contentDescription = "Share",
            tint = Color.White // 图标颜色为白色
        )
    }
}

@Preview
@Composable
fun DetailPreview(){
    val example = Pelicula(
        id = 1,
        url = "https://www.tvmaze.com/shows/1/under-the-dome",
        name = "Under the Dome",
        type = "Scripted",
        language = "English",
        genres = listOf("Drama", "Science-Fiction", "Thriller"),
        status = "Ended",
        runtime = 60,
        averageRuntime = 60,
        premiered = "2013-06-24",
        ended = "2015-09-10",
        officialSite = "http://www.cbs.com/shows/under-the-dome/",
        schedule = Schedule(
            time = "22:00",
            days = listOf("Thursday")
        ),
        rating = 6.5,
        weight = 98,
        network = Network(
            id = 2,
            name = "CBS",
            country = Country(
                name = "United States",
                code = "US",
                timezone = "America/New_York"
            ),
            officialSite = "https://www.cbs.com/"
        ),
        webChannel = null,
        dvdCountry = null,
        externals = Externals(
            tvrage = 25988,
            thetvdb = 264492,
            imdb = "tt1553656"
        ),
        image = Image(
            medium = "https://static.tvmaze.com/uploads/images/medium_portrait/81/202627.jpg",
            original = "https://static.tvmaze.com/uploads/images/original_untouched/81/202627.jpg"
        ),
        summary = "<p><b>Under the Dome</b> is the story of a small town that is suddenly and inexplicably sealed off from the rest of the world by an enormous transparent dome. The town's inhabitants must deal with surviving the post-apocalyptic conditions while searching for answers about the dome, where it came from and if and when it will go away.</p>",
        updated = 1704794065,
        _links = Links(
            self = Link("https://api.tvmaze.com/shows/1"),
            previousepisode = Link("https://api.tvmaze.com/episodes/185054")
        )
    )

    Practica3Theme {
        FlickDetailPage(pelicula = example, onBack = {})
    }
}