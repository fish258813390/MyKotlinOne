package neil.com.kotlinone.base

import android.app.Application
import android.content.ComponentCallbacks2
import com.bumptech.glide.Glide
import neil.com.kotlinone.BuildConfig

/**
 *
 * @author neil
 * @date 2018/2/22
 */
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            // 内存泄漏检测
        }
        Preference.setContext(applicationContext)
    }

    // 应用处于低内存时处理
    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        // 清除glide 缓存
        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            Glide.get(this).clearMemory()
        }
        // 清除内存
        Glide.get(this).trimMemory(level)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        // 处理低内存时
        Glide.get(this).clearMemory()
    }


}