package neil.com.kotlinone.utils

import android.util.Log
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import neil.com.kotlinone.api.GithubService
import neil.com.kotlinone.constant.Constant
import neil.com.kotlinone.utils.client.RetrofitService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Retrofit 工具类 封装
 * @author neil
 * @date 2018/2/9
 */
class RetrofitUtils<T> {

    // 伴生对象
    companion object {

        // 创建Retrofit
        fun create(url: String): Retrofit {
            // 日志显示级别
            val level: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY
            // 新建log拦截器

            val loggingInterceptor: HttpLoggingInterceptor =
                    HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                        override fun log(message: String?) {
                            Log.d("loggingInterceptor",message)
                        }
                    })

            //
//            val loggingInterceptor1: HttpLoggingInterceptor = HttpLoggingInterceptor(
//                    HttpLoggingInterceptor.Logger { message: String? ->
//                Log.d("loggingInterceptor", message)
//            })

            loggingInterceptor.level = level
            val okHttpClientBuilder = OkHttpClient().newBuilder()

            okHttpClientBuilder.connectTimeout(60, TimeUnit.SECONDS)
            okHttpClientBuilder.readTimeout(60, TimeUnit.SECONDS)
            okHttpClientBuilder.writeTimeout(60, TimeUnit.SECONDS)

            // 添加拦截器
            okHttpClientBuilder.addInterceptor(loggingInterceptor)

            return Retrofit.Builder()
                    .baseUrl(url)
                    .client(okHttpClientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
        }

        /**
         * 获取Service Api
         */
        fun <T> getService(url: String, service: Class<T>): T {
            return create(url).create(service)
        }

        val retrofitService: GithubService = RetrofitUtils.
                getService(Constant.REQUEST_BASE_URL, GithubService::class.java)


        val retrofitClientService: RetrofitService = RetrofitUtils.
                getService(Constant.REQUEST_CLIENT_BASE_URL, RetrofitService::class.java)

    }
}

