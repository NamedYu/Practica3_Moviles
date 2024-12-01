package cn.yu.practica3

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
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cn.yu.practica3.ui.theme.Practica3Theme
import coil.compose.rememberImagePainter // 使用 Coil 来加载网络图片
import java.nio.file.Paths

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlickApp(
    modifier: Modifier = Modifier,
    lalista: List<Pelicula>,
    onPeliClick: (Pelicula) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Show") })
        }
    ) { innerPadding ->
        PeliculaList(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            peliculalista = lalista,
            onPeliClick = onPeliClick
        )
    }
}

@Composable
fun PeliculaList(
    modifier: Modifier = Modifier,
    peliculalista: List<Pelicula>,
    onPeliClick: (Pelicula) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // 将 peliculalista 列表分组，每组两个项目
        peliculalista.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // 为每个项目创建 PeliculaItem
                rowItems.forEach { pelicula ->
                    PeliculaItem(
                        pelicula = pelicula,
                        onPeliClick = onPeliClick,
                        modifier = Modifier.weight(1f) // 每个项目均分空间
                    )
                }
            }
        }
    }
}

@Composable
fun PeliculaItem(
    pelicula: Pelicula,
    onPeliClick: (Pelicula) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {

    }
    Card(
        modifier = modifier
            .padding(8.dp)
            .clickable { onPeliClick(pelicula) },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), // 调整内边距
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 使用 Box 来包裹 Image 和 Rating
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .fillMaxWidth()
            ) {
                // 使用 Coil 加载图像
                val imageUrl = pelicula.image?.medium ?: "your_default_image_url" // 设置默认图像 URL
                Image(
                    painter = rememberImagePainter(imageUrl),
                    contentDescription = pelicula.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop // 裁剪图像以适应大小
                )

                // 左上角显示 Rating
                pelicula.rating?.let {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart) // 定位到左上角
                            .padding(4.dp) // 添加内边距避免紧贴边缘
                            .background(
                                Color(0xFFBBE1FA),
                                shape = RoundedCornerShape(12.dp, 12.dp, 12.dp, 12.dp)
                            ) // 上边圆角
                            .padding(horizontal = 6.dp, vertical = 2.dp) // 内部文字的边距
                    ) {
                        Text(
                            text = "$it",
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp)) // 添加间距

            Text(
                text = pelicula.name ?: "Unknown",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Text(
                text = pelicula.genres?.joinToString(separator = ",") ?:"Hola",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}




@Preview(showBackground = true)
@Composable
fun FlickAppPreview() {
    // 示例数据用于预览
    val examplePeliculas = listOf(
        Pelicula(
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
        ),
        Pelicula(
            id = 2,
            url = "https://www.tvmaze.com/shows/2/person-of-interest",
            name = "Person of Interest",
            type = "Scripted",
            language = "English",
            genres = listOf("Action", "Crime", "Science-Fiction"),
            status = "Ended",
            runtime = 60,
            averageRuntime = 60,
            premiered = "2011-09-22",
            ended = "2016-06-21",
            officialSite = "http://www.cbs.com/shows/person_of_interest/",
            schedule = Schedule(
                time = "22:00",
                days = listOf("Tuesday")
            ),
            rating = 8.8,
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
                tvrage = 28376,
                thetvdb = 248742,
                imdb = "tt1839578"
            ),
            image = Image(
                medium = "https://static.tvmaze.com/uploads/images/medium_portrait/163/407679.jpg",
                original = "https://static.tvmaze.com/uploads/images/original_untouched/163/407679.jpg"
            ),
            summary = "<p>You are being watched. The government has a secret system, a machine that spies on you every hour of every day. I know because I built it. I designed the Machine to detect acts of terror but it sees everything. Violent crimes involving ordinary people. People like you. Crimes the government considered \"irrelevant\". They wouldn't act so I decided I would. But I needed a partner. Someone with the skills to intervene. Hunted by the authorities, we work in secret. You'll never find us. But victim or perpetrator, if your number is up, we'll find you.</p>",
            updated = 1709303296,
            _links = Links(
                self = Link("https://api.tvmaze.com/shows/2"),
                previousepisode = Link("https://api.tvmaze.com/episodes/659372")
            )
        )
    )
    Practica3Theme {
        FlickApp(lalista = examplePeliculas, onPeliClick = {}) {
            
        }
    }
}
