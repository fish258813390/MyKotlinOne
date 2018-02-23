package neil.com.kotlinone.utils.client

/**
 *
 * @author neil
 * @date 2018/2/22
 */
object RetrofitHelper {

    private const val TAG = "RetrofitHelper"
    private const val CONTENT_PRE = "OkHttp: "
    private const val SAVE_USER_LOGIN_KEY = "user/login"
    private const val SAVE_USER_REGISTER_KEY = "user/register"
    private const val SET_COOKIE_KEY = "set-cookie"
    private const val COOKIE_NAME = "Cookie"
    private const val CONNECT_TIMEOUT = 30L
    private const val READ_TIMEOUT = 30L


//    val retrofitService: RetrofitService =
//            RetrofitHelper.getService(Constant.REQUEST_CLIENT_BASE_URL, RetrofitService::class.java)
//
//
//    private fun <T> getService(url: String, service: Class<T>): T
//            = create(url).create(service)

//    private fun create(url: String): Retrofit {
//        OkHttpClient().newBuilder().apply {
//            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
//            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
//            addInterceptor {
//
//            }
//        }
//    }

}