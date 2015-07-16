package samples.okhttp

import java.io.IOException

import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response

fun fetch(url: String): String {
    val request = Request.Builder().url(url).build()

    val client = OkHttpClient()
    val response = client.newCall(request).execute()
    println(response.headers().names())
    return response.body().string()
}

fun main(args: Array<String>) {
    val response = fetch("https://raw.github.com/square/okhttp/master/README.md")
    println(response)
}
