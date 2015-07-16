package samples.retrofit

import retrofit.Call
import retrofit.Callback
import retrofit.MoshiConverterFactory
import retrofit.Response
import retrofit.Retrofit
import retrofit.http.GET
import retrofit.http.Path
import retrofit.http.Query

data class Repo(val id: Int,
                val description: String,
                val name: String,
                val url: String)

interface GitHubService {
    @GET("/users/{user}/repos?per_page=1")
    fun listRepos(@Path("user") user: String,
                  @Query("page") page: Int): Call<MutableList<Repo>>
}

fun GitHubService.listAllRepos(user: String): List<Repo> {
    val repos = linkedSetOf<Repo>()
    var page = 1

    while (true) {
        val response = this.listRepos(user, page).execute()
        if (response.body().isEmpty()) {
            break
        }

        repos.addAll(response.body())
        page += 1
    }
    return repos.toList()
}

fun main(args: Array<String>) {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .converterFactory(MoshiConverterFactory.create())
        .build()

    val service = retrofit.create(javaClass<GitHubService>())

//    val repos = service.listRepos("octocat", 1).execute().body()
//    repos.forEach { println(it) }
//
//    val repos2 = service.listRepos("octocat", 2).execute().body()
//    repos2.forEach { println(it) }

    service.listAllRepos("octocat").forEach { println(it) }

//    service.listRepos("octocat", 1).enqueue(object : Callback<MutableList<Repo>> {
//        override fun onResponse(response: Response<MutableList<Repo>>) {
//            println(response.headers())
//        }
//
//        override fun onFailure(error: Throwable) {
//            throw error
//        }
//    })
}
