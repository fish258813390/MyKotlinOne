package neil.com.kotlinone

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import com.just.agentweb.AgentWeb
import com.just.agentweb.ChromeClientCallbackManager
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.JobCancellationException
import neil.com.kotlinone.constant.Constant

/**
 *
 * @author neil
 * @date 2018/2/22
 */
/**
 * Log
 */
fun loge(tag: String, content: String?) {
    Log.e(tag, content ?: tag)
}

fun Deferred<Any>?.cancelByActive() = this?.run {
    tryCatch {
        if (isActive) {
            cancel()
        }
    }
}

fun tryCatch(catchBlock: (Throwable) -> Unit = {}, tryBlock: () -> Unit) {
    try {
        tryBlock()
    } catch (_: JobCancellationException) {

    } catch (t: Throwable) {
        catchBlock(t)
    }
}

// 通用toast
fun Context.toast(content: String) {
    Constant.showToast?.apply {
        Log.d("dddddddddddddddddd","000000000")
        setText(content)
        show()
    } ?: run {
        Log.d("dddddddddddddddddd","11111")
        Toast.makeText(this.applicationContext, content, Toast.LENGTH_SHORT).apply {
            Constant.showToast = this
        }.show()
    }
}

// getAgentWebview
fun String.getAgentWeb(
        activity: Activity, webContent: ViewGroup, layoutParams: ViewGroup.LayoutParams,
        receivedTitleCallback: ChromeClientCallbackManager.ReceivedTitleCallback?
) = AgentWeb.with(activity) // 传入activity or Fragment
        .setAgentWebParent(webContent, layoutParams)  // 传入AgentWeb 父空间
        .useDefaultIndicator() // 使用默认进度条
        .defaultProgressBarColor()  // 使用默认进度条颜色
        .setReceivedTitleCallback(receivedTitleCallback) // //设置 Web 页面的 title 回调
        .createAgentWeb()
        .ready()
        .go(this)!!