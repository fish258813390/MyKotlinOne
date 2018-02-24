package neil.com.kotlinone.utils.client

import io.reactivex.Observable
import kotlinx.coroutines.experimental.Deferred
import neil.com.kotlinone.bean.client.ArticleListResponse
import neil.com.kotlinone.bean.client.BannerResponse
import neil.com.kotlinone.bean.client.HomeListResponse
import neil.com.kotlinone.bean.client.TreeListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

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
    fun getHomeList(@Path("page") page: Int): Deferred<HomeListResponse>

    @GET("/article/list/{page}/json")
    fun getHomeList1(@Path("page") page: Int): Observable<HomeListResponse>

    /**
     * 首页banner
     */
    @GET("/banner/json")
    fun getBanner(): Deferred<BannerResponse>

    @GET("/banner/json")
    fun getBanner1(): Observable<BannerResponse>

    /**
     * 知识体系
     * http://www.wanandroid.com/tree/json
     */
    @GET("/tree/json")
    fun getTypeTreeList(): Observable<TreeListResponse>

    /**
     * 知识体系下的文章
     * http://www.wanandroid.com/article/list/0/json?cid=168
     * @param page page
     * @param cid cid
     */
    @GET("/article/list/{page}/json")
    fun getArticleList(@Path("page") page: Int, @Query("cid") cid: Int): Observable<ArticleListResponse>

}
