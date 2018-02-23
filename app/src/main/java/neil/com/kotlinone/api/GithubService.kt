package neil.com.kotlinone.api

import io.reactivex.Observable
import neil.com.kotlinone.bean.StarUser
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * Github API
 * 获取项目star明细
 * @author neil
 * @date 2018/2/9
 */
interface GithubService {

    @GET("/repos/yinjinyj/SuperCalendar/stargazers")
    fun getProjectStarList(): Call<List<StarUser>>

    @GET("/repos/yinjinyj/SuperCalendar/stargazers")
    fun getStarList(): Observable<List<StarUser>>
}

object GBService {

    val githubService: GithubService by lazy {
        Retrofit.Builder().
                baseUrl("https://api.github.com").
                addConverterFactory(GsonConverterFactory.create()).
                build().
                create(GithubService::class.java)
    }

}

