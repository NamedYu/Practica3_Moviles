package cn.yu.practica3
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import android.content.Context

open class DataProvider(context: Context?) {
    var peliculas: List<Pelicula>

    init {
        var jsonString = ""
        if(context!=null){
        jsonString = loadJSONFromAsset(context, "shows.json")}

        val jsonArray = JSONArray(jsonString)
        val temp = mutableListOf<Pelicula>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)

            val id = jsonObject.getInt("id")
            val url = jsonObject.getString("url")
            val name = jsonObject.getString("name")
            val type = jsonObject.getString("type")
            val language = jsonObject.getString("language")
            val genres = jsonObject.getJSONArray("genres")
            val genresList = mutableListOf<String>()

            for (j in 0 until genres.length()) {
                genresList.add(genres.getString(j))
            }

            val status = jsonObject.getString("status")
            val runtime = jsonObject.optInt("runtime") // optInt用于可空类型
            val averageRuntime = jsonObject.optInt("averageRuntime")
            val premiered = jsonObject.getString("premiered")
            val ended = jsonObject.optString("ended")
            val officialSite = jsonObject.optString("officialSite")
            val scheduleJson = jsonObject.getJSONObject("schedule")
            val schedule = Schedule(
                time = scheduleJson.getString("time"),
                days = scheduleJson.getJSONArray("days").let { daysArray ->
                    List(daysArray.length()) { daysArray.getString(it) }
                }
            )
            val rating = jsonObject.optJSONObject("rating")?.getDouble("average") // 如果 rating 可能为 null
            val weight = jsonObject.getInt("weight")

            val networkJson = jsonObject.getJSONObject("network")
            val network = Network(
                id = networkJson.getInt("id"),
                name = networkJson.getString("name"),
                country = networkJson.optJSONObject("country")?.let { countryJson ->
                    Country(
                        name = countryJson.getString("name"),
                        code = countryJson.getString("code"),
                        timezone = countryJson.getString("timezone")
                    )
                },
                officialSite = networkJson.optString("officialSite")
            )

            val webChannel = jsonObject.opt("webChannel")
            val dvdCountry = jsonObject.opt("dvdCountry")
            val externalsJson = jsonObject.optJSONObject("externals")
            val externals = Externals(
                tvrage = externalsJson?.getInt("tvrage"),
                thetvdb = externalsJson?.getInt("thetvdb"),
                imdb = externalsJson?.getString("imdb")
            )

            val imageJson = jsonObject.getJSONObject("image")
            val image = Image(
                medium = imageJson.getString("medium"),
                original = imageJson.getString("original")
            )

            val summary = jsonObject.optString("summary")
            val updated = jsonObject.getLong("updated")
            val linksJson = jsonObject.getJSONObject("_links")
            val links = Links(
                self = Link(linksJson.getJSONObject("self").getString("href")),
                previousepisode = linksJson.optJSONObject("previousepisode")?.let {
                    Link(it.getString("href"))
                }
            )

            // 创建 Pelicula 对象并添加到临时列表中
            val show = Pelicula(
                id, url, name, type, language, genresList, status, runtime, averageRuntime,
                premiered, ended, officialSite, schedule, rating, weight,
                network, webChannel, dvdCountry, externals, image, summary, updated, links
            )

            temp.add(show)
        }

        peliculas = temp
    }

    private fun loadJSONFromAsset(context: Context, fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }

    fun shuffleShows() {
        peliculas = peliculas.shuffled()
    }
}