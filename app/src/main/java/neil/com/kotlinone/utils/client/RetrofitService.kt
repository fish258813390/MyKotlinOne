package neil.com.kotlinone.utils.client

import io.reactivex.Observable
import kotlinx.coroutines.experimental.Deferred
import neil.com.kotlinone.api.GithubService
import neil.com.kotlinone.bean.client.BannerResponse
import neil.com.kotlinone.bean.client.HomeListResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *
 * @author neil
 * @date 2018/2/22
 */
interface RetrofitService {


    /**
     * 首页数据
     * http://www.wanandroid.com/article/list/0/json
     * @param page page
     */
    @GET("/article/list/{page}/json")
    fun getHomeList(
            @Path("page") page: Int
    ): Deferred<HomeListResponse>

    @GET("/article/list/{page}/json")
    fun getHomeList1(
            @Path("page") page: Int
    ): Observable<HomeListResponse>


    /**
     * 首页banner
     */
    @GET("/banner/json")
    fun getBanner(): Deferred<BannerResponse>


    @GET("/banner/json")
    fun getBanner1(): Observable<BannerResponse>

}
